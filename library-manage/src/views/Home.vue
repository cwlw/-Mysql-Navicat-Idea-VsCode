<template>
  <div class="home-container">
    <!-- 顶部检索区域 -->
    <el-card class="search-card">
      <el-input
        v-model="keyword"
        placeholder="输入关键词检索"
        suffix-icon="el-icon-search"
        style="width: 300px; margin-right: 10px"
        @keyup.enter="handleSearch"
        @input="handleSearch"
      />
      <el-button type="primary" @click="handleSearch">检索</el-button>
      <el-button @click="resetSearch">重置</el-button>
    </el-card>

    <!-- 检索结果区域 -->
    <div v-if="keyword.trim()" class="search-result-wrap">
      <h3>检索结果（共{{ searchResultList.length }}本图书）</h3>
      <div class="book-item-list">
        <div 
          class="book-item" 
          v-for="item in searchResultList" 
          :key="item.ISBN"
          @click="$router.push({path:'/bookDetail', query:{isbn: item.ISBN}})"
        >
          <!-- 图书封面：根据ISBN动态拼接图片路径 + 加载失败兜底（此处已修改） -->
          <div class="book-cover">
            <img
              :src="`/book_cover/${item.ISBN}.png`"
              alt="图书封面"
              class="cover-img"
              @error="handleImgError"
            />
          </div>

          <!-- 图书核心信息 -->
          <div class="book-info">
            <p class="book-title">
              <span>[图书]</span> {{ item.bname }}
            </p>
            <p>索书号：{{ item.clcNum || '无索书号' }}</p>
            <p>责任者：{{ item.author || '' }}</p>
            <p>出版信息：{{ item.ISBN }} {{ item.publisher || '' }}，{{ item.pubDate || '' }}</p>
            <p class="desc">内容与摘要附注：{{ item.introduction || '无内容简介' }}</p>
          </div>

          <!-- 馆藏数量标签 -->
          <div class="book-status">
            <el-tag type="info">
              纸本({{ item.totalNum }}) / 可借({{ item.availableNum }})
            </el-tag>
          </div>
        </div>
      </div>
      <!-- 无数据空状态 -->
      <el-empty v-if="searchResultList.length === 0" description="未查询到匹配的图书"></el-empty>
    </div>

    <!-- 三大榜单区域 -->
    <div v-else class="rank-wrap">
      <!-- 热书推荐 标题带查看全部 默认排序 -->
      <el-card class="rank-card">
        <template #header>
          <div class="card-header">
            <span>热书推荐</span>
            <el-link type="primary" @click="$router.push('/allBook')">查看全部</el-link>
          </div>
        </template>
        <CommonTable :columns="hotBookColumns" :data="hotBooks" />
      </el-card>

      <!-- 借书明星 按借阅次数排序 -->
      <el-card class="rank-card">
        <template #header>
          <div class="card-header">
            <span>借书明星</span>
            <el-link type="primary" @click="$router.push('/allBook?sort=borrow')">查看全部</el-link>
          </div>
        </template>
        <CommonTable :columns="authorRankColumns" :data="authorRankList" />
      </el-card>

      <!-- 新书速递 按出版时间由近到远 -->
      <el-card class="rank-card">
        <template #header>
          <div class="card-header">
            <span>新书速递</span>
            <el-link type="primary" @click="$router.push('/allBook?sort=new')">查看全部</el-link>
          </div>
        </template>
        <CommonTable :columns="newBookColumns" :data="newBooks" />
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import CommonTable from '../components/CommonTable.vue'
import { bookApi, bookcopyApi, borrowrecApi } from '../api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const keyword = ref('')

// 页面渲染数据
const hotBooks = ref([])
const authorRankList = ref([])
const newBooks = ref([])
const searchResultList = ref([])

// 原始数据源
const allBook = ref([])
const allBookCopy = ref([])
const allBorrow = ref([])
const bookBorrowMap = ref({})

// 【核心映射】ISBN -> 总副本数、可借数量
const bookStockMap = ref({})

// 日期格式化：清除 T 及时分秒
const formatPubDate = (val) => {
  if (!val) return ''
  return val.split('T')[0]
}

// 图片加载失败处理：替换为暂无封面
const handleImgError = (e) => {
  e.target.style.display = 'none'
  e.target.parentElement.innerHTML = '<div class="no-cover">暂无封面</div>'
}

// 表格列配置
const hotBookColumns = [
  { prop: 'bname', label: '书名' },
  { prop: 'author', label: '作者' }
]
const authorRankColumns = [
  { prop: 'author', label: '作者' },
  { prop: 'borrowTotal', label: '累计借阅次数' }
]
const newBookColumns = [
  { prop: 'bname', label: '书名' },
  { prop: 'author', label: '作者' },
  { prop: 'pubDate', label: '出版时间' }
]

// 加载所有基础数据 + 统计馆藏数量
const loadAllData = async () => {
  try {
    const [bookRes, copyRes, borrowRes] = await Promise.all([
      bookApi.list(),
      bookcopyApi.list(),
      borrowrecApi.list()
    ])

    // 处理图书数据：格式化日期
    let rawBooks = bookRes.code === 200 ? bookRes.data || [] : []
    allBook.value = rawBooks.map(book => ({
      ...book,
      pubDate: formatPubDate(book.pubDate)
    }))

    allBookCopy.value = copyRes.code === 200 ? copyRes.data || [] : []
    allBorrow.value = borrowRes.code === 200 ? borrowRes.data || [] : []

    // 统计馆藏：总副本数、可借数量
    calcBookStock()

    // 统计借阅排行数据
    calcRankData()
  } catch (err) {
    ElMessage.error('页面数据加载失败，请检查后端服务')
    console.error('加载异常：', err)
    // 异常清空列表，避免脏数据残留
    hotBooks.value = []
    authorRankList.value = []
    newBooks.value = []
  }
}

// 统计每本图书的 总副本数、可借数量
const calcBookStock = () => {
  const stock = {}
  allBookCopy.value.forEach(copy => {
    const isbn = copy.ISBN
    if (!stock[isbn]) {
      stock[isbn] = { totalNum: 0, availableNum: 0 }
    }
    stock[isbn].totalNum += 1
    if (copy.status === 1) {
      stock[isbn].availableNum += 1
    }
  })
  bookStockMap.value = stock
}

// 统计借阅排行（热书、作者、新书）
const calcRankData = () => {
  const barCodeToIsbn = {}
  allBookCopy.value.forEach(item => {
    barCodeToIsbn[item.barCode] = item.ISBN
  })

  const tempCountMap = {}
  allBorrow.value.forEach(borrowItem => {
    const isbn = barCodeToIsbn[borrowItem.barCode]
    if (isbn) tempCountMap[isbn] = (tempCountMap[isbn] || 0) + 1
  })
  bookBorrowMap.value = tempCountMap

  hotBooks.value = [...allBook.value]
    .map(book => ({
      ...book,
      borrowCount: bookBorrowMap.value[book.ISBN] || 0
    }))
    .sort((a, b) => b.borrowCount - a.borrowCount)
    .slice(0, 5)

  const authorTotalMap = {}
  allBook.value.forEach(book => {
    const count = bookBorrowMap.value[book.ISBN] || 0
    if (count > 0) authorTotalMap[book.author] = (authorTotalMap[book.author] || 0) + count
  })
  authorRankList.value = Object.entries(authorTotalMap)
    .map(([author, borrowTotal]) => ({ author, borrowTotal }))
    .sort((a, b) => b.borrowTotal - a.borrowTotal)
    .slice(0, 5)

  newBooks.value = [...allBook.value]
    .sort((a, b) => new Date(b.pubDate) - new Date(a.pubDate))
    .slice(0, 5)
}

// 检索逻辑（增加字段空值判断，防止报错）
const handleSearch = () => {
  const key = keyword.value.trim()
  searchResultList.value = []
  if (!key) return

  const filterList = allBook.value.filter(item =>
    item.bname?.includes(key) || item.author?.includes(key)
  )

  searchResultList.value = filterList.map(book => {
    const stock = bookStockMap.value[book.ISBN] || { totalNum: 0, availableNum: 0 }
    return {
      ...book,
      totalNum: stock.totalNum,
      availableNum: stock.availableNum
    }
  })
}

// 重置检索
const resetSearch = () => {
  keyword.value = ''
  searchResultList.value = []
}

// 跳转登录（预留方法）
const toLogin = () => {
  router.push('/login')
}

onMounted(() => loadAllData())
</script>

<style scoped>
.home-container {
  padding: 20px;
  box-sizing: border-box;
}

/* 检索卡片 */
.search-card {
  margin-bottom: 12px;
}
:deep(.search-card .el-card__body) {
  padding: 16px 20px !important;
  min-height: unset !important;
}

/* 卡片标题布局：文字左 查看全部右 */
:deep(.card-header) {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

/* 检索结果区域 */
.search-result-wrap {
  width: 100%;
}
.search-result-wrap h3 {
  margin: 0 0 16px 0;
  font-size: 18px;
  color: #333;
}
.book-item-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
.book-item {
  display: flex;
  gap: 20px;
  padding: 16px;
  background: #ffffff;
  border-radius: 6px;
  box-shadow: 0 1px 4px rgba(0, 0, 0.08);
  align-items: flex-start;
}

/* 图书封面 */
.book-cover {
  width: 100px;
  height: 140px;
  flex-shrink: 0;
  border: 1px solid #eee;
  overflow: hidden;
}
.cover-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.no-cover {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  font-size: 12px;
}

.book-info {
  flex: 1;
}
.book-title {
  font-size: 16px;
  margin: 0 0 8px 0;
}
.book-title span {
  color: #666;
}
.book-info p {
  margin: 6px 0;
  color: #333;
}
.desc {
  color: #666;
  line-height: 1.6;
}

.book-status {
  flex-shrink: 0;
  margin-top: 4px;
}

/* 三栏榜单布局 */
.rank-wrap {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}
.rank-card {
  flex: 1;
  min-width: 300px;
}
:deep(.el-card__body) {
  padding: 12px;
  min-height: 340px;
}
:deep(.el-table) {
  width: 100%;
}
:deep(.el-table__row:nth-child(even)) {
  background-color: #f8f9fa;
}
</style>