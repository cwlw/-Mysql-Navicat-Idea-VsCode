<template>
  <div class="catalog-container" style="padding: 20px;">
    <el-tabs v-model="activeTab">
      <!-- 新书入库 Tab -->
      <el-tab-pane label="新书入库" name="add">
        <!-- 顶部搜索工具栏 -->
        <el-row align="middle" style="margin-bottom: 18px; gap:10px;">
          <el-input
            v-model="searchKeyword"
            placeholder="输入书名/作者/图书条码查询"
            clearable
            style="width: 420px"
          />
          <el-button type="primary" @click="searchBook">查询</el-button>
          <el-button @click="scanTip">扫描条形码</el-button>
          <el-button type="success" @click="openBookDialog">新增图书</el-button>
        </el-row>

        <!-- 图书主列表表格 -->
        <el-table
          :data="bookList"
          border
          style="width: 100%; margin-bottom:16px"
          :row-style="getTableRowStyle"
        >
          <el-table-column label="ISBN" prop="ISBN" />
          <el-table-column label="书名" prop="bname" />
          <el-table-column label="作者" prop="author" />
          <el-table-column label="出版社" prop="publisher" />
          <!-- 出版日期格式化，去除T及后面时间 -->
          <el-table-column label="出版日期">
            <template #default="scope">
              {{ formatDate(scope.row.pubDate) }}
            </template>
          </el-table-column>
          <el-table-column label="图书简介" prop="introduction" />
          <el-table-column label="分类号" prop="clcNum" />
          <!-- 图书总状态【已修正】 -->
          <el-table-column label="图书总状态">
            <template #default="scope">
              {{ scope.row.bookStatus == 1 ? '可借' : scope.row.bookStatus == 2 ? '已借出' : '下架' }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="160">
            <template #default="scope">
              <el-button size="small" @click="openEdit(scope.row)">编辑</el-button>
              <el-button size="small" type="warning" @click="handleDown(scope.row.id)">下架</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 标准分页组件 -->
        <el-pagination
          :current-page="pageNum"
          :page-size="pageSize"
          :total="total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />

        <!-- 选中图书后 副本管理区域 -->
        <div v-if="currentBook" style="margin-top:30px; border:1px solid #eee; padding:16px;">
          <h3 style="margin:0 0 12px;">副本管理：{{ currentBook.bname }}</h3>
          <el-button type="success" @click="openCopyDialog" style="margin-bottom:12px">新增副本</el-button>
          <el-table :data="copyList" border style="width:100%">
            <el-table-column label="副本条码" prop="barCode" />
            <el-table-column label="馆藏位置" prop="place" />
            <el-table-column label="馆藏地" prop="location" />
            <el-table-column label="当前所在地" prop="nowLocation" />
            <!-- 副本状态【完全修正，匹配数据库1可借/2已借出/3下架】 -->
            <el-table-column label="副本状态">
              <template #default="scope">
                <span :class="{textOrange: scope.row.status===1, textRed: scope.row.status===3}">
                  {{ scope.row.status === 1 ? '可借' : scope.row.status === 2 ? '已借出' : '下架' }}
                </span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
              <template #default="scope">
                <el-button size="small" type="danger" @click="cancelCopy(scope.row.barCode)">注销</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <!-- 新增/编辑图书弹窗 -->
        <el-dialog v-model="bookDialogVisible" title="图书信息" width="650">
          <el-form :model="book" label-width="90px">
            <el-form-item label="ISBN">
              <el-input v-model="book.ISBN" />
            </el-form-item>
            <el-form-item label="书名">
              <el-input v-model="book.bname" />
            </el-form-item>
            <el-form-item label="作者">
              <el-input v-model="book.author" />
            </el-form-item>
            <el-form-item label="出版社">
              <el-input v-model="book.publisher" />
            </el-form-item>
            <el-form-item label="出版日期">
              <el-date-picker v-model="book.pubDate" type="date" placeholder="选择日期" />
            </el-form-item>
            <el-form-item label="分类号">
              <el-input v-model="book.clcNum" />
            </el-form-item>
            <el-form-item label="图书简介">
              <el-input v-model="book.introduction" type="textarea" rows="3" />
            </el-form-item>
          </el-form>
          <template #footer>
            <el-button @click="bookDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitBook">保存</el-button>
          </template>
        </el-dialog>

        <!-- 新增副本弹窗 -->
        <el-dialog v-model="copyDialogVisible" title="新增图书副本" width="520">
          <el-form :model="copyForm" label-width="90px">
            <el-form-item label="关联ISBN">
              <el-input v-model="copyForm.ISBN" disabled />
            </el-form-item>
            <el-form-item label="副本条码">
              <el-input v-model="copyForm.barCode" />
            </el-form-item>
            <el-form-item label="馆藏存放位置">
              <el-input v-model="copyForm.place" />
            </el-form-item>
            <el-form-item label="馆藏地">
              <el-input v-model="copyForm.location" />
            </el-form-item>
            <el-form-item label="当前所在地">
              <el-input v-model="copyForm.nowLocation" />
            </el-form-item>
          </el-form>
          <template #footer>
            <el-button @click="copyDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitCopy">保存副本</el-button>
          </template>
        </el-dialog>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { catalogueApi } from '@/api/index'

const activeTab = ref('add')
const searchKeyword = ref('')
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const bookList = ref([])
const currentBook = ref(null)
const copyList = ref([])
const bookDialogVisible = ref(false)
const copyDialogVisible = ref(false)

// 新增图书默认状态改为1（可借）
const book = reactive({
  id: null,
  ISBN: '',
  bname: '',
  author: '',
  publisher: '',
  pubDate: '',
  clcNum: '',
  introduction: '',
  bookStatus: 1
})

// 新增副本默认状态1（可借）
const copyForm = reactive({
  barCode: '',
  ISBN: '',
  place: '',
  location: '',
  nowLocation: '',
  status: 1,
  oldStatus: 1
})

const getTableRowStyle = ({ row }) => row.isDown ? { background: '#eeee' } : {}

// 日期格式化方法：截取T前面年月日
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.split('T')[0]
}

// 每页条数切换：重置到第一页
const handleSizeChange = () => {
  pageNum.value = 1
  searchBook()
}

// 切换页码
const handleCurrentChange = (newPage) => {
  pageNum.value = newPage
  searchBook()
}

const searchBook = async () => {
  try {
    const params = {
      keyword: searchKeyword.value,
      pageNum: pageNum.value,
      pageSize: pageSize.value
    }
    const res = await catalogueApi.getBookPage(params)
    bookList.value = res.records
    total.value = res.total
    currentBook.value = null
    copyList.value = []
  } catch (err) {
    ElMessage.error('查询失败：' + err.message)
  }
}

const scanTip = async () => {
  const res = await catalogueApi.scanTip()
  ElMessage.info(res.msg)
}

const openBookDialog = () => {
  book.id = null
  book.ISBN = ''
  book.bname = ''
  book.author = ''
  book.publisher = ''
  book.pubDate = ''
  book.clcNum = ''
  book.introduction = ''
  book.bookStatus = 1
  bookDialogVisible.value = true
  currentBook.value = null
  copyList.value = []
}

const openEdit = async (row) => {
  currentBook.value = row
  Object.assign(book, row)
  // 编辑回显时也去掉T
  if (book.pubDate) {
    book.pubDate = formatDate(book.pubDate)
  }
  bookDialogVisible.value = true
  const res = await catalogueApi.getCopyListByIsbn(row.ISBN)
  copyList.value = res.data
}

const submitBook = async () => {
  if (!book.ISBN || !book.bname) return ElMessage.warning('ISBN和书名不能为空')
  const res = book.id ? await catalogueApi.updateBook(book) : await catalogueApi.addBook(book)
  if (res.code === 200) {
    ElMessage.success(res.msg)
    bookDialogVisible.value = false
    searchBook()
  } else {
    ElMessage.error(res.msg)
  }
}

const handleDown = async (id) => {
  await ElMessageBox.confirm('确认下架该图书，所有副本同步下架', '提示')
  const res = await catalogueApi.downBook(id)
  if (res.code === 200) {
    ElMessage.success('下架成功')
    searchBook()
  } else {
    ElMessage.error(res.msg)
  }
}

const openCopyDialog = () => {
  copyForm.barCode = ''
  copyForm.ISBN = currentBook.value.ISBN
  copyForm.place = ''
  copyForm.location = ''
  copyForm.nowLocation = ''
  copyForm.status = 1
  copyForm.oldStatus = 1
  copyDialogVisible.value = true
}

const submitCopy = async () => {
  if (!copyForm.barCode) return ElMessage.warning('副本条码不能为空')
  try {
    const res = await catalogueApi.addCopy(copyForm)
    if (res.code === 200) {
      ElMessage.success('新增副本成功')
      copyDialogVisible.value = false
      const listRes = await catalogueApi.getCopyListByIsbn(copyForm.ISBN)
      copyList.value = listRes.data
    } else {
      ElMessage.error(res.msg)
    }
  } catch (err) {
    ElMessage.error('新增副本失败')
  }
}

const cancelCopy = async (barCode) => {
  await ElMessageBox.confirm('确认注销该副本，注销后无法借阅', '警告')
  const res = await catalogueApi.cancelCopy(barCode)
  if (res.code === 200) {
    ElMessage.success('注销成功')
    const listRes = await catalogueApi.getCopyListByIsbn(currentBook.value.ISBN)
    copyList.value = listRes.data
  } else {
    ElMessage.error(res.msg)
  }
}

searchBook()
</script>

<style scoped>
.textOrange {
  color: orange;
}
.textRed {
  color: red;
}
</style>