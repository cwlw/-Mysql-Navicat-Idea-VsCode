<template>
  <div class="all-book-container">
    <h2>{{ pageTitle }}</h2>
    <div class="book-item-list">
      <div 
        class="book-item" 
        v-for="item in bookList" 
        :key="item.ISBN"
        @click="$router.push({path:'/bookDetail', query:{isbn: item.ISBN}})"
      >
        <div class="book-cover">
          <!-- 修改：使用item.ISBN拼接图片地址 -->
          <img
            :src="`/book_cover/${item.ISBN}.png`"
            alt="图书封面"
            class="cover-img"
            @error="handleImgError"
          />
        </div>
        <div class="book-info">
          <p class="book-title">
            <span>[图书]</span> {{ item.bname }}
          </p>
          <p>索书号：{{ item.clcNum || '无索书号' }}</p>
          <p>责任者：{{ item.author || '' }}</p>
          <p>出版信息：{{ item.ISBN }} {{ item.publisher || '' }}，{{ item.pubDate || '' }}</p>
          <p class="desc">内容与摘要附注：{{ item.introduction || '无内容简介' }}</p>
        </div>
        <div class="book-status">
          <el-tag type="info">纸本({{ item.totalNum }}) / 可借({{ item.availableNum }})</el-tag>
        </div>
      </div>
    </div>
    <el-empty v-if="bookList.length === 0" description="暂无图书数据"></el-empty>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { bookApi, bookcopyApi, borrowrecApi } from '../api'
import { ElMessage } from 'element-plus'

const route = useRoute()
const pageTitle = ref('全部图书')
const bookList = ref([])
const allBook = ref([])
const allBookCopy = ref([])
const allBorrow = ref([])
const bookStockMap = ref({})
const bookBorrowMap = ref({})

// 图片加载失败
const handleImgError = (e) => {
  e.target.style.display = 'none'
  e.target.parentElement.innerHTML = '<div class="no-cover">暂无封面</div>'
}

// 日期格式化
const formatPubDate = (val) => {
  if (!val) return ''
  return val.split('T')[0]
}

// 统计馆藏
const calcBookStock = () => {
  const stock = {}
  allBookCopy.value.forEach(copy => {
    const isbn = copy.ISBN
    if (!stock[isbn]) stock[isbn] = { totalNum: 0, availableNum: 0 }
    stock[isbn].totalNum += 1
    if (copy.status === 1) stock[isbn].availableNum += 1
  })
  bookStockMap.value = stock
}

// 统计借阅次数
const calcBorrowCount = () => {
  const barCodeToIsbn = {}
  allBookCopy.value.forEach(item => barCodeToIsbn[item.barCode] = item.ISBN)
  const temp = {}
  allBorrow.value.forEach(borrow => {
    const isbn = barCodeToIsbn[borrow.barCode]
    if (isbn) temp[isbn] = (temp[isbn] || 0) + 1
  })
  bookBorrowMap.value = temp
}

// 加载数据并按类型排序 + 切换页面标题
const loadData = async () => {
  try {
    const [bookRes, copyRes, borrowRes] = await Promise.all([
      bookApi.list(),
      bookcopyApi.list(),
      borrowrecApi.list()
    ])
    let rawBooks = bookRes.code === 200 ? bookRes.data || [] : []
    allBook.value = rawBooks.map(book => ({
      ...book,
      pubDate: formatPubDate(book.pubDate)
    }))
    allBookCopy.value = copyRes.code === 200 ? copyRes.data || [] : []
    allBorrow.value = borrowRes.code === 200 ? borrowRes.data || [] : []

    calcBookStock()
    calcBorrowCount()

    // 组装完整图书数据
    let fullList = allBook.value.map(book => {
      const stock = bookStockMap.value[book.ISBN] || { totalNum: 0, availableNum: 0 }
      return {
        ...book,
        totalNum: stock.totalNum,
        availableNum: stock.availableNum,
        borrowCount: bookBorrowMap.value[book.ISBN] || 0
      }
    })

    // 根据路由参数 设置标题 + 排序
    const sortType = route.query.sort
    switch (sortType) {
      case 'borrow':
        pageTitle.value = '借书明星'
        fullList.sort((a, b) => b.borrowCount - a.borrowCount)
        break
      case 'new':
        pageTitle.value = '新书速递'
        fullList.sort((a, b) => new Date(b.pubDate) - new Date(a.pubDate))
        break
      default:
        pageTitle.value = '热书推荐'
    }

    bookList.value = fullList
  } catch (err) {
    ElMessage.error('加载图书失败')
    console.error(err)
    bookList.value = []
  }
}

onMounted(() => loadData())
</script>

<style scoped>
.all-book-container {
  padding: 20px;
}
.all-book-container h2 {
  margin: 0 0 20px 0;
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
  background: #fff;
  border-radius: 6px;
  box-shadow: 0 1px 4px rgba(0,0,0.08);
  align-items: flex-start;
}
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
</style>