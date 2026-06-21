<template>
  <div class="library-profile-wrap">
    <!-- 左侧侧边栏 -->
    <div class="left-sidebar">
      <div class="sidebar-title">我的图书馆</div>
      <div class="sidebar-menu">
        <div 
          class="menu-item" 
          :class="{ active: activeMenu === 'bookList' }"
          @click="handleMenuClick('bookList')"
        >我的书单</div>
        <div 
          class="menu-item" 
          :class="{ active: activeMenu === 'request' }"
          @click="handleMenuClick('request')"
        >我的请求</div>
        <div 
          class="menu-item" 
          :class="{ active: activeMenu === 'info' }"
          @click="handleMenuClick('info')"
        >个人信息</div>
      </div>
    </div>

    <!-- 中间主内容区域 -->
    <div class="main-content">
      <!-- 我的书单页面 -->
      <div v-if="activeMenu === 'bookList'">
        <div class="top-tab">
          <span class="tab-active">今日推荐</span>
          <span class="tab-normal">每日一本好书</span>
        </div>

        <div class="book-recommend-card" v-if="hotBook">
          <div class="book-cover">
            <img
              v-if="hotBook"
              :src="`/book_cover/${hotBook.ISBN}.png`"
              alt="图书封面"
              class="cover-img"
              @error="handleImgError"
            />
            <div class="no-cover" v-else>暂无封面</div>
          </div>
          <div class="book-info">
            <h3 class="book-name">{{ hotBook.bname }}</h3>
            <p class="book-author">责任者：{{ hotBook.author || "未知作者" }}</p>
            <p class="book-desc">暂无摘要内容</p>
          </div>
        </div>
        <div class="book-recommend-card" v-else>
          <p>暂无推荐图书</p>
        </div>

        <div class="stat-row">
          <div class="stat-card">
            <div class="stat-icon">📍</div>
            <div class="stat-text">请求到书</div>
            <div class="stat-num">0</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">⏱️</div>
            <div class="stat-text">借书超期</div>
            <div class="stat-num">{{ overDueNum }}</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">🔔</div>
            <div class="stat-text">未处理行为</div>
            <div class="stat-num">0</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">💰</div>
            <div class="stat-text">我的欠款</div>
            <div class="stat-num text-red">{{ totalFine }}.00元</div>
          </div>
        </div>

        <div class="borrow-block">
          <div class="block-header">
            <h3>我的借阅</h3>
          </div>
          <div class="borrow-tab">
            <span>按借阅时间</span>
          </div>
          <el-table
            :data="borrowAllList"
            border
            class="borrow-table"
            width="100%"
            scroll-x
          >
            <el-table-column prop="barcode" label="条码号" min-width="160" />
            <el-table-column prop="name" label="书名" min-width="180" />
            <el-table-column prop="borrowDate" label="借书日期" min-width="180" />
            <el-table-column prop="dueDate" label="应还日期" min-width="180" />
            <el-table-column prop="realRetDate" label="实际归还日期" min-width="180" />
            <el-table-column prop="status" label="状态" min-width="100" />
          </el-table>
          <el-pagination
            v-model:current-page="borrowPage"
            v-model:page-size="borrowSize"
            :total="borrowTotal"
            layout="total, sizes, prev, pager, next"
            @size-change="loadAllBorrow"
            @current-change="loadAllBorrow"
          />
        </div>

        <el-divider />
        <el-card title="缴纳罚款">
          <el-table :data="fineRecords" border @selection-change="handleSelectionChange">
            <el-table-column type="selection" />
            <el-table-column prop="name" label="书名" />
            <el-table-column prop="fine" label="罚款金额" />
            <el-table-column prop="status" label="状态" />
          </el-table>
          <p class="pay-tip">已选金额合计：{{ selectedTotal }}元</p>
          <el-button type="primary" @click="openPayDialog">确认支付</el-button>
        </el-card>
      </div>

      <!-- 罚款支付确认弹窗 -->
      <el-dialog v-model="payDialogVisible" title="支付确认" width="500px">
        <div class="pay-dialog-content">
          <p class="dialog-title">请确认以下缴费信息</p>
          <div class="pay-item-list">
            <div class="pay-item" v-for="(item, index) in selectedRecords" :key="index">
              <span class="book-name">图书：{{ item.name }}</span>
              <span class="fine-price">单笔罚款：{{ item.fine }}元</span>
            </div>
          </div>
          <div class="total-pay">合计支付金额：<span class="total-price">{{ selectedTotal }}元</span></div>
        </div>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="payDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitPayFine" :loading="payLoading">确认支付</el-button>
          </div>
        </template>
      </el-dialog>

      <!-- 我的请求页面 -->
      <div v-if="activeMenu === 'request'" class="empty-block">
        <el-card title="我的请求">
          <div style="padding: 60px 0; text-align: center; color: #999; font-size: 14px;">
            暂无图书请求记录，可前往首页提交资源荐购申请
          </div>
        </el-card>
      </div>

      <!-- 个人信息页面 -->
      <div v-if="activeMenu === 'info'" class="info-page-wrap">
        <div class="info-header">
          <el-avatar size="120" icon="User" />
          <h2 class="user-name">{{ userInfo.username || "暂无姓名" }}</h2>
          <p class="school-name">重庆理工大学</p>
        </div>

        <div class="info-block">
          <h3 class="block-title">账号资料</h3>
          <div class="two-col-row">
            <div class="col-item">
              <span class="lab">账号：</span>
              <span class="val">{{ userInfo.sno || "未获取" }}</span>
              <!-- 修改1：绑定打开弹窗事件 -->
              <el-button text type="success" class="operate-btn" @click="openStuPwdDialog">修改密码</el-button>
            </div>
            <div class="col-item">
              <span class="lab">邮箱：</span>
              <span class="val">{{ userInfo.email || "" }}</span>
              <el-button v-if="!userInfo.email" text type="success" class="operate-btn">添加</el-button>
            </div>
          </div>
          <div class="two-col-row">
            <div class="col-item">
              <span class="lab">手机号：</span>
              <span class="val">{{ userInfo.phone || "" }}</span>
              <el-button v-if="!userInfo.phone" text type="success" class="operate-btn">添加</el-button>
            </div>
            <div class="col-item"></div>
          </div>
        </div>

        <div class="info-block">
          <h3 class="block-title">基本信息</h3>
          <div class="two-col-row">
            <div class="col-item"><span class="lab">系别：</span><span class="val">{{ userInfo.collage || "未填写" }}</span></div>
            <div class="col-item"><span class="lab">证号：</span><span class="val">{{ userInfo.sno || "未获取" }}</span></div>
          </div>
          <div class="two-col-row">
            <div class="col-item"><span class="lab">专业：</span><span class="val">{{ userInfo.major || "未填写" }}</span></div>
            <div class="col-item"><span class="lab">欠款：</span><span class="val text-red">{{ totalFine }}元</span></div>
          </div>
        </div>
      </div>
    </div>

    <!-- 右侧侧边栏 -->
    <div class="right-sidebar" v-if="activeMenu === 'bookList'">
      <div class="right-card">
        <div class="card-header">
          <span>我的积分</span>
          <span class="more-btn">更多></span>
        </div>
        <p class="card-subtitle">努力换来收获</p>
        <div class="score-item">
          <span>● 浏览资源</span>
          <span class="score-add">+1分</span>
        </div>
        <div class="score-item">
          <span>● 浏览资源</span>
          <span class="score-add">+1分</span>
        </div>
      </div>

      <div class="right-card mt16">
        <div class="card-header">
          <span>行为记录</span>
          <span class="more-btn">更多></span>
        </div>
      </div>

      <div class="info-card mt16">
        <p>姓名：{{ userInfo.username || "暂无姓名" }}</p>
        <p>借书证号：{{ userInfo.cardNo || "暂无借书证" }}</p>
        <p>当前未缴罚款：{{ totalFine }}元</p>
      </div>
    </div>

    <!-- 修改2：新增学生修改密码弹窗，放在所有弹窗最后 -->
    <el-dialog v-model="stuPwdDialogVisible" title="修改登录密码" width="480px">
      <el-form :model="stuPwdForm" label-width="100px">
        <el-form-item label="原密码">
          <el-input v-model="stuPwdForm.oldPwd" show-password></el-input>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="stuPwdForm.newPwd" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认新密码">
          <el-input v-model="stuPwdForm.confirmPwd" show-password></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="stuPwdDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitStuUpdatePwd" :loading="stuPwdLoading">确认修改</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
// 修改3：新增导入updatePwd、useRouter
import { getStudentProfile, getUnpaidFine, getBorrowHistory, updatePwd } from '@/api'
import { bookApi, bookcopyApi, borrowrecApi } from '@/api'
import { useRouter } from 'vue-router'

const router = useRouter()
const activeMenu = ref('bookList')
const handleMenuClick = (key) => {
  activeMenu.value = key
}

// 基础数据
const userInfo = ref({})
const fineRecords = ref([])
const selectedRecords = ref([])
const hotBook = ref(null)
const payDialogVisible = ref(false)
const payLoading = ref(false)

// ========== 新增：修改密码弹窗变量与方法 ==========
const stuPwdDialogVisible = ref(false)
const stuPwdLoading = ref(false)
const stuPwdForm = ref({
  accountId: "",
  oldPwd: "",
  newPwd: "",
  confirmPwd: ""
})
// 打开弹窗
const openStuPwdDialog = () => {
  stuPwdForm.value.accountId = userInfo.value.sno
  stuPwdForm.value.oldPwd = ""
  stuPwdForm.value.newPwd = ""
  stuPwdForm.value.confirmPwd = ""
  stuPwdDialogVisible.value = true
}
// 提交修改密码
const submitStuUpdatePwd = async () => {
  const form = stuPwdForm.value
  if (!form.oldPwd || !form.newPwd || !form.confirmPwd) {
    return ElMessage.warning("原密码、新密码、确认密码不能为空")
  }
  if (form.newPwd !== form.confirmPwd) {
    return ElMessage.warning("两次输入的新密码不一致")
  }
  stuPwdLoading.value = true
  try {
    const res = await updatePwd(form)
    if (res.code === 200) {
      ElMessage.success(res.msg)
      stuPwdDialogVisible.value = false
      // 清空登录缓存，跳转登录页
      localStorage.removeItem('isLogin')
      localStorage.removeItem('userType')
      router.push('/login')
    } else {
      ElMessage.error(res.msg)
    }
  } catch (err) {
    ElMessage.error("网络异常，密码修改失败")
  } finally {
    stuPwdLoading.value = false
  }
}

// 借阅分页数据
const borrowAllList = ref([])
const borrowPage = ref(1)
const borrowSize = ref(10)
const borrowTotal = ref(0)

// 图书推荐数据
const allBook = ref([])
const allBookCopy = ref([])
const allBorrow = ref([])
const bookBorrowMap = ref({})

// 计算属性
const overDueNum = computed(() => {
  return borrowAllList.value.filter(item => item.status === '未归还').length
})

// 总未缴罚款
const totalFine = computed(() => {
  return fineRecords.value.reduce((sum, item) => sum + Number(item.fine), 0)
})

// 选中订单总金额
const selectedTotal = computed(() => {
  return selectedRecords.value.reduce((sum, item) => sum + Number(item.fine), 0)
})

// 表格选中事件
const handleSelectionChange = (val) => selectedRecords.value = val
const handleImgError = (e) => e.target.style.display = 'none'

// 打开支付确认弹窗
const openPayDialog = () => {
  if (selectedRecords.value.length === 0) {
    ElMessage.warning('请先选择需要缴费的罚款订单')
    return
  }
  payDialogVisible.value = true
}

// 加载借阅记录
const loadAllBorrow = async () => {
  const res = await getBorrowHistory(borrowPage.value, borrowSize.value)
  if (res.code === 200) {
    borrowAllList.value = res.data.list
    borrowTotal.value = res.data.total
  }
}

// 统计图书借阅热度
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

// 加载热门推荐图书
const loadHotBook = async () => {
  try {
    const [bookRes, copyRes, borrowRes] = await Promise.all([
      bookApi.list(),
      bookcopyApi.list(),
      borrowrecApi.list()
    ])
    allBook.value = bookRes.code === 200 ? bookRes.data || [] : []
    allBookCopy.value = copyRes.code === 200 ? copyRes.data || [] : []
    allBorrow.value = borrowRes.code === 200 ? borrowRes.data || [] : []
    calcBorrowCount()
    let fullList = allBook.value.map(book => ({
      ...book,
      borrowCount: bookBorrowMap.value[book.ISBN] || 0
    }))
    fullList.sort((a, b) => b.borrowCount - a.borrowCount)
    hotBook.value = fullList[0] || null
  } catch (err) {
    console.error("加载推荐图书失败", err)
  }
}

// 加载用户数据 & 未缴罚款数据（仅查询展示，无任何数据库修改操作）
const loadAllData = async () => {
  const userType = localStorage.getItem('userType')
  if (!userType) {
    ElMessage.warning('请先登录')
    location.href = '/login'
    return
  }
  if (userType === 'admin') {
    ElMessage.error('管理员无法访问学生个人中心页面')
    location.href = '/admin/profile'
    return
  }
  try {
    const profileRes = await getStudentProfile()
    if (profileRes.code === 200) {
      const data = profileRes.data
      userInfo.value = {
        username: data.student.username,
        sno: data.student.sno,
        collage: data.student.collage,
        major: data.student.major,
        email: data.student.email,
        phone: data.student.phone,
        cardNo: data.libcard?.cardNo || "暂无借书证",
        cardStatus: data.libcard?.cardStatus || "正常",
        times: data.libcard?.times || 0
      }
    }
    // 拉取未缴纳罚款用于页面展示
    const fineRes = await getUnpaidFine()
    if (fineRes.code === 200) {
      const rawFineList = fineRes.data || []
      fineRecords.value = rawFineList.filter(item =>
        Number(item.fine) > 0 && item.status === '未缴纳'
      )
    }
    await loadAllBorrow()
  } catch (err) {
    console.error('数据加载异常', err)
    ElMessage.error('页面数据加载失败，请刷新重试')
  }
}

// 纯前端模拟支付：不调用任何修改数据库接口，仅弹窗提示成功
const submitPayFine = async () => {
  payLoading.value = true
  try {
    const payTotal = selectedTotal.value
    ElMessage.success(`缴费成功，共支付${payTotal}元`)
    payDialogVisible.value = false
    selectedRecords.value = []
  } catch (err) {
    ElMessage.error('操作失败')
    console.error(err)
  } finally {
    payLoading.value = false
  }
}

onMounted(async () => {
  await loadAllData()
  await loadHotBook()
})
</script>

<style scoped>
.library-profile-wrap {
  display: flex;
  gap: 16px;
  padding: 16px;
  background: #f5f7fa;
  min-height: calc(100vh - 60px);
}
.left-sidebar {
  width: 180px;
  background: #fff;
  border-radius: 6px;
  padding: 16px 0;
}
.sidebar-title {
  font-size: 18px;
  font-weight: bold;
  padding: 0 16px 12px;
  color: #009688;
}
.menu-item {
  padding: 10px 16px;
  cursor: pointer;
  font-size: 14px;
}
.menu-item.active {
  background: #e6f7f5;
  color: #009688;
}
.main-content { flex: 1; }
.top-tab { margin-bottom: 12px; }
.tab-active {
  font-size: 16px;
  font-weight: bold;
  margin-right: 20px;
  cursor: pointer;
}
.tab-normal { color: #666; cursor: pointer; }
.book-recommend-card {
  display: flex;
  gap: 16px;
  background: #fff;
  padding: 16px;
  border-radius: 6px;
  margin-bottom: 16px;
}
.book-cover {
  width: 100px;
  height: 140px;
  background: #eee;
  flex-shrink: 0;
  position: relative;
}
.cover-img { width: 100%; height: 100%; object-fit: cover; }
.no-cover {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  color: #999;
  position: absolute;
  top: 0; left: 0;
}
.book-name { color: #009688; margin: 0 0 8px; }
.book-author, .book-desc { color: #666; margin: 4px 0; font-size: 14px; }
.stat-row {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 12px;
  margin-bottom: 16px;
}
.stat-card {
  background: #fff;
  padding: 16px;
  border-radius: 6px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.stat-icon { font-size: 24px; margin-bottom: 8px; }
.stat-text { font-size: 14px; color: #333; }
.stat-num { font-size: 20px; font-weight: bold; margin-top: 4px; }
.text-red { color: #f53f3f; }

.borrow-block {
  background: #fff;
  padding: 16px;
  border-radius: 6px;
  margin-bottom: 16px;
  overflow-x: auto;
}
.borrow-table {
  min-width: 980px;
}
:deep(.borrow-table .el-table__cell) {
  white-space: normal !important;
  word-break: break-all;
}

.block-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}
.more-btn { color: #009688; cursor: pointer; font-size: 14px; }
.borrow-tab { margin-bottom: 12px; }
.pay-tip { margin: 12px 0; font-size: 14px; }
.right-sidebar { width: 260px; }
.right-card {
  background: #fff;
  padding: 16px;
  border-radius: 6px;
}
.info-card {
  background: #fff;
  padding: 16px;
  border-radius: 6px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}
.card-subtitle { color: #999; font-size: 13px; margin-bottom: 12px; }
.score-item {
  display: flex;
  justify-content: space-between;
  padding: 4px 0;
  font-size: 14px;
}
.score-add { color: #009688; }
.mt16 { margin-top: 16px; }

.empty-block {
  width: 100%;
}

/* 个人信息页面样式 */
.info-page-wrap {
  padding: 40px 60px;
  background: #fff;
  border-radius: 6px;
  min-height: 700px;
}
.info-header {
  text-align: center;
  margin-bottom: 40px;
}
.user-name {
  font-size: 32px;
  font-weight: 600;
  margin: 12px 0 6px;
}
.school-name {
  color: #666;
  font-size: 16px;
  margin: 0;
}
.info-block {
  margin-bottom: 36px;
}
.block-title {
  font-size: 24px;
  font-weight: 500;
  padding-bottom: 12px;
  border-bottom: 1px solid #eee;
  margin-bottom: 20px;
}
.two-col-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  margin-bottom: 18px;
}
.col-item {
  display: flex;
  align-items: center;
  font-size: 16px;
}
.lab {
  min-width: 80px;
  color: #333;
}
.val {
  color: #444;
  margin-right: 8px;
}
.operate-btn {
  color: #36b37e;
  padding: 0;
}

/* 支付弹窗样式 */
.pay-dialog-content {
  padding: 10px 0;
}
.dialog-title {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 20px;
  text-align: center;
}
.pay-item-list {
  border: 1px solid #eee;
  border-radius: 6px;
  padding: 16px;
  margin-bottom: 20px;
  max-height: 200px;
  overflow-y: auto;
}
.pay-item {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px solid #f5f5f5;
}
.pay-item:last-child {
  border-bottom: none;
}
.book-name {
  color: #333;
}
.fine-price {
  color: #666;
}
.total-pay {
  text-align: center;
  font-size: 18px;
  font-weight: 500;
}
.total-price {
  color: #f53f3f;
  margin-left: 8px;
}
.dialog-footer {
  text-align: right;
}
</style>