<template>
  <div class="detail-page">
    <div class="main-wrap">
      <!-- 左侧主体区域 -->
      <div class="left-content">
        <div class="title-box">
          <h2>【图书】{{ bookInfo.bname || '未知书名' }}</h2>
        </div>
        <div class="top-box">
          <div class="cover-box">
            <img
              v-if="hasValidIsbn"
              :src="getCoverUrl()"
              alt="图书封面"
              class="cover-img"
              @error="handleImgError"
            />
            <div class="no-cover" v-else>暂无封面</div>
            <!-- 评分放在封面下方 -->
            <div class="score">0.0分（{{ bookInfo.evalCount || 0 }}人评价）</div>
          </div>
          <div class="info-right">
            <el-tabs v-model="activeTab">
              <el-tab-pane label="基本信息" name="base">
                <!-- 基础信息保持原样不动 -->
                <div class="info-list">
                  <p><span class="label">【责任者】</span>{{ bookInfo.author || '' }}</p>
                  <p><span class="label">【出版发行项】</span>{{ bookInfo.publisher }},{{ bookInfo.pubDate }}</p>
                  <p><span class="label">【ISBN及定价】</span>{{ bookInfo.ISBN }}/CNY{{ bookInfo.price || '无' }}.00</p>
                  <p><span class="label">【载体形态项】</span>{{ bookInfo.page || '无' }}页;图;24cm</p>
                  <p><span class="label">【中图法分类号】</span>{{ bookInfo.clcNum || '无' }}</p>
                  <p><span class="label">【提要文摘附注】</span>{{ bookInfo.introduction || '无内容简介' }}</p>
                </div>
              </el-tab-pane>
              <el-tab-pane label="机读格式" name="format">
                <p>标准MARC机读数据（预留扩展）</p>
              </el-tab-pane>
            </el-tabs>
          </div>
        </div>

        <div class="btn-group">
          <el-button icon="el-icon-star-off">收藏</el-button>
          <el-button icon="el-icon-share">引用</el-button>
          <el-button icon="el-icon-share">分享</el-button>
          <el-button icon="el-icon-crop">二维码</el-button>
        </div>

        <div class="stock-wrap">
          <el-tabs v-model="stockTab">
            <el-tab-pane label="本馆馆藏" name="local">
              <el-table :data="stockList" border>
                <el-table-column label="序号" type="index" width="60" />
                <el-table-column label="索书号" prop="clcNum" />
                <el-table-column label="条码号" prop="barCode" />
                <el-table-column label="年卷期" prop="yearVolume" />
                <el-table-column label="馆藏地" prop="location" />
                <el-table-column label="当前所在地" prop="nowLocation" />
                <el-table-column label="排架号" prop="shelfNum" />
                <el-table-column label="书刊状态" prop="statusText" />
                <el-table-column label="借阅" prop="borrowText" />
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="电子馆藏" name="elect">
              <p>暂无电子资源</p>
            </el-tab-pane>
            <el-tab-pane label="书目详情" name="catalog">
              <p>完整书目元数据（预留扩展）</p>
            </el-tab-pane>
          </el-tabs>
        </div>

        <!-- 最底部：请求/委托预约区域 -->
        <div class="request-block">
          <h3>请求</h3>
          <el-button type="success" class="pre-book-btn">委托/预约</el-button>
          <!-- 登录判断区域 -->
          <div v-if="isLogin" class="request-desc">
            <span>请求者：{{ loginUser }}</span>
            <span>| 可发起委托次数：20次</span>
            <span>| 可发起预约次数：20次</span>
            <el-button type="primary" class="req-btn">请求</el-button>
          </div>
          <div v-else class="request-tip">
            请先登录账号后再发起请求、预约
          </div>
          <el-table :data="requestTable" border style="margin-top:12px;">
            <el-table-column label="序号" type="index" width="60"/>
            <el-table-column label="索书号" prop="clcNum"/>
            <el-table-column label="校区" prop="campus"/>
            <el-table-column label="馆藏地" prop="location"/>
            <el-table-column label="可借馆藏" prop="canBorrow"/>
            <el-table-column label="已请求数" prop="reqNum"/>
          </el-table>
        </div>
      </div>

      <!-- 右侧侧边栏：相关资源、作者其他著作、相关借阅 -->
      <div class="right-sidebar">
        <div class="side-card">
          <h4>相关资源</h4>
          <div class="resource-icons">
            <a :href="doubanUrl" target="_blank" title="豆瓣搜索本书">
              <img src="/images/douban.png" alt="豆瓣" class="icon-img" />
            </a>
            <a :href="baiduUrl" target="_blank" title="百度搜索本书">
              <img src="/images/baidu.png" alt="百度" class="icon-img" />
            </a>
          </div>
        </div>
        <!-- 作者其他著作，点击跳转详情 -->
        <div class="side-card">
          <h4>作者其他著作</h4>
          <div class="book-list">
            <div v-for="book in authorOtherBookList" :key="book.id" class="book-item">
              <router-link :to="{ path:'/bookDetail', query:{ isbn: book.ISBN } }">
                {{ book.bname }}
              </router-link>
            </div>
            <span v-if="authorOtherBookList.length === 0">暂无作者其他著作</span>
          </div>
        </div>
        <!-- 相关借阅 -->
        <div class="side-card">
          <h4>相关借阅</h4>
          <div class="borrow-list">
            <div v-for="borrow in relateBorrowList" :key="borrow.id" class="borrow-item">
              <div class="book-cover-small">暂无封面</div>
              <div class="book-info-sm">
                <p class="name">{{ borrow.bname }}</p>
                <p class="desc">责任者：{{ borrow.author }}</p>
                <p class="isbn">ISBN：{{ borrow.ISBN }}</p>
              </div>
            </div>
            <span v-if="relateBorrowList.length === 0">暂无相关借阅图书</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { bookApi, bookcopyApi } from '../api'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const bookInfo = ref({ bname: '', ISBN: '', author: '' })
const stockList = ref([])
const activeTab = ref('base')
const stockTab = ref('local')
const requestTable = ref([])
const allBook = ref([])

// 右侧侧边栏数据
const relateResourceList = ref([])
const authorOtherBookList = ref([])
const relateBorrowList = ref([])

// 登录相关状态
const isLogin = ref(false)
const loginUser = ref('曹建业')

// 拼接搜索链接
const doubanUrl = computed(() => {
  const name = encodeURIComponent(bookInfo.value.bname || '')
  return `https://search.douban.com/book/subject_search?search_text=${name}&cat=1001`
})
const baiduUrl = computed(() => {
  const name = encodeURIComponent(bookInfo.value.bname || '')
  return `https://www.baidu.com/s?wd=${name} 图书`
})

// ISBN判断计算属性
const hasValidIsbn = computed(() => {
  const isbn = bookInfo.value.ISBN?.trim()
  return !!isbn
})

// 拼接ISBN图片路径
const getCoverUrl = () => {
  const isbn = bookInfo.value.ISBN?.trim()
  return `/book_cover/${isbn}.png`
}

// 图片加载失败处理
const handleImgError = (e) => {
  e.target.style.display = 'none'
  const parent = e.target.parentElement
  if (!parent.querySelector('.no-cover')) {
    const div = document.createElement('div')
    div.className = 'no-cover'
    div.textContent = '暂无封面'
    parent.appendChild(div)
  }
}

// 加载全部数据
const loadDetail = async () => {
  const isbn = route.query.isbn
  if (!isbn) {
    ElMessage.warning('缺少图书标识')
    router.push('/home')
    return
  }

  try {
    const bookRes = await bookApi.list()
    allBook.value = bookRes.data || []
    console.log('全部图书', allBook.value)

    const targetBook = allBook.value.find(item => item.ISBN === isbn)
    console.log('匹配到的图书:', targetBook)
    if (!targetBook) {
      ElMessage.error('未找到该图书')
      return
    }

    const mappedBook = {
      ...targetBook,
      bname: targetBook.bname || targetBook.bookName || targetBook.name || targetBook.title || '未知书名',
      author: targetBook.author || ''
    }
    bookInfo.value = mappedBook
    console.log('当前图书作者：', bookInfo.value.author)

    // 筛选同作者其他著作
    const currAuthor = bookInfo.value.author
    const currIsbn = bookInfo.value.ISBN
    authorOtherBookList.value = allBook.value.filter(book => {
      return book.author === currAuthor && book.ISBN !== currIsbn
    })

    // 加载馆藏副本
    const copyRes = await bookcopyApi.list()
    const copies = copyRes.data.filter(c => c.ISBN === isbn)
    stockList.value = copies.map((item, idx) => {
      let statusText = '不可借'
      let borrowText = '--'
      const dueDate = item.dueDate || ''
      if (item.status === 1) {
        statusText = '在架'
        borrowText = '可借'
      } else if (item.status === 2) {
        statusText = dueDate ? `借出 - 应还日期:${dueDate}` : '借出'
        borrowText = '--'
      }
      return {
        id: idx + 1,
        clcNum: targetBook.clcNum || '无',
        barCode: item.barCode || '无',
        yearVolume: item.yearVolume || '-',
        location: item.location || '无',
        nowLocation: item.nowLocation || '无',
        shelfNum: item.place || '无',
        statusText,
        borrowText,
        status: item.status
      }
    })

    // 动态生成请求表格
    const groupMap = {}
    stockList.value.forEach(item => {
      const loc = item.nowLocation
      if (!groupMap[loc]) {
        groupMap[loc] = {
          total: 0,
          borrow: 0
        }
      }
      groupMap[loc].total += 1
      if (item.status === 2) {
        groupMap[loc].borrow += 1
      }
    })

    const tempReq = []
    Object.keys(groupMap).forEach(loc => {
      const group = groupMap[loc]
      const campus = loc.split('-')[0] || loc
      tempReq.push({
        clcNum: targetBook.clcNum || '无',
        campus: campus,
        location: loc,
        canBorrow: group.total - group.borrow,
        reqNum: 0
      })
    })
    requestTable.value = tempReq

    relateResourceList.value = []
    relateBorrowList.value = []

  } catch (err) {
    ElMessage.error('加载图书失败')
    console.error('加载异常：', err)
  }
}

// 首次进入执行
onMounted(() => loadDetail())

// 新增：监听路由isbn变化，自动重新加载数据
watch(() => route.query.isbn, (newIsbn, oldIsbn) => {
  if (newIsbn && newIsbn !== oldIsbn) {
    loadDetail()
  }
})
</script>

<style scoped>
.detail-page {
  width: 100%;
}
.main-wrap {
  display: flex;
  gap: 24px;
}
.left-content {
  flex: 1;
}
.right-sidebar {
  width: 280px;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 16px;
}
.side-card {
  border: 1px solid #e5e7eb;
  border-radius: 4px;
  padding: 12px;
}
.side-card h4 {
  margin: 0 0 10px 0;
  font-size: 16px;
  color: #333;
  border-bottom: 1px solid #eee;
  padding-bottom: 6px;
}
.resource-icons {
  display: flex;
  gap: 16px;
}
.icon-img {
  width: 32px;
  height: 32px;
}
/* 著作链接样式 */
.book-item a {
  color: #0050b3;
  text-decoration: none;
}
.book-item a:hover {
  text-decoration: underline;
}
/* 封面区域 */
.title-box {
  margin-bottom: 16px;
}
.title-box h2 {
  margin: 0;
  font-size: 22px;
}
.top-box {
  display: flex;
  gap: 24px;
  margin-bottom: 20px;
  align-items: flex-start;
}
.cover-box {
  width: 180px;
  flex-shrink: 0;
}
.cover-img {
  width: 100%;
  height: 240px;
  object-fit: cover;
  border: 1px solid #eee;
}
.no-cover {
  width: 100%;
  height: 240px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f5f5;
  color: #999;
}
.score {
  margin-top: 8px;
  color: #666;
}
.info-right {
  flex: 1;
}
.info-list p {
  margin: 10px 0;
  font-size: 15px;
}
.label {
  color: #333;
  font-weight: 500;
  min-width: 120px;
  display: inline-block;
}
.btn-group {
  margin: 20px 0;
}
.stock-wrap {
  margin-top: 20px;
}
/* 底部请求模块 */
.request-block {
  margin-top: 30px;
  border-top: 1px solid #eee;
  padding-top: 20px;
}
.request-block h3 {
  font-size: 18px;
  margin: 0 0 12px 0;
}
.pre-book-btn {
  background: #00b42a;
  color: #fff;
  border: none;
  margin-bottom: 10px;
}
.request-desc {
  display: flex;
  align-items: center;
  gap: 16px;
  background: #e6f7ff;
  padding: 10px;
}
.request-tip {
  background: #fff2e8;
  color: #fa8c16;
  padding: 10px;
}
.req-btn {
  margin-left: auto;
}
/* 侧边列表 */
.book-item, .borrow-item {
  margin-bottom: 8px;
  line-height: 1.5;
}
.borrow-item {
  display: flex;
  gap: 8px;
  padding: 6px 0;
  border-bottom: 1px solid #f2f2f2;
}
.book-cover-small {
  width: 60px;
  height: 80px;
  background: #f5f5f5;
  font-size: 12px;
  color: #999;
  display: flex;
  align-items: center;
  justify-content: center;
}
.book-info-sm p {
  margin: 2px 0;
  font-size: 13px;
}
.name {
  font-weight: 500;
  color: #0050b3;
}
.desc, .isbn {
  color: #666;
}
</style>