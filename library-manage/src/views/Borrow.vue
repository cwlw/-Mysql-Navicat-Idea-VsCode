<template>
  <div class="borrow-container">
    <!-- 借书操作区域 -->
    <el-card title="借书操作" style="margin-bottom: 20px">
      <el-form :model="borrowForm" label-width="80px">
        <el-form-item label="借书证号">
          <el-input v-model="borrowForm.cardNo" placeholder="输入或扫描借书证号" />
          <el-button type="primary" @click="checkCard" :loading="cardLoading">检查证件</el-button>
        </el-form-item>
        <div v-if="cardValid">
          <el-form-item label="书籍条码">
            <div style="display:flex;gap:10px">
              <el-input v-model="borrowForm.barCode" placeholder="扫描/输入图书副本条码" />
              <el-button>扫码条码</el-button>
              <el-button @click="searchBook">查询图书</el-button>
            </div>
          </el-form-item>
          <el-table :data="borrowList" border style="margin:10px 0">
            <el-table-column prop="barcode" label="图书条码" />
            <el-table-column prop="isbn" label="ISBN" />
            <el-table-column prop="name" label="书名" />
            <el-table-column prop="place" label="馆藏地" />
            <el-table-column label="操作">
              <template #default="scope">
                <el-button type="danger" link @click="delBook(scope.$index)">移除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button 
            type="primary" 
            @click="submitBorrow" 
            v-if="borrowList.length>0"
            :loading="submitLoading"
          >
            确认借书
          </el-button>
        </div>
      </el-form>
    </el-card>

    <!-- 还书操作区域（修复第80行标签缩进，无换行断裂） -->
    <el-card title="还书操作">
      <el-form :model="returnForm" label-width="80px">
        <el-form-item label="书籍条码">
          <el-input v-model="returnForm.bookBarcode" placeholder="扫描书籍条码" />
          <el-button type="primary" @click="submitReturn">确认还书</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 学生信息弹窗 -->
    <el-dialog title="学生信息确认" v-model="studentDialog.visible" width="500px">
      <div v-if="studentDialog.data">
        <p>学号：{{ studentDialog.data.student.sno }}</p>
        <p>姓名：{{ studentDialog.data.student.username }}</p>
        <p>学院：{{ studentDialog.data.student.collage }}</p>
        <p>剩余可借次数：{{ studentDialog.data.libcard.times }}</p>
        <p>借书证状态：{{ studentDialog.data.libcard.cardStatus === 1 ? '正常' : studentDialog.data.libcard.cardStatus === 2 ? '挂失' : '注销' }}</p>
      </div>
      <template #footer>
        <el-button @click="studentDialog.visible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { borrowApi } from '@/api/index'

const cardValid = ref(false)
const borrowList = ref([])
const cardLoading = ref(false)
const submitLoading = ref(false)

// 弹窗数据
const studentDialog = reactive({
  visible: false,
  data: null
})

// 借书表单：绑定图书条码
const borrowForm = reactive({
  cardNo: '',
  barCode: ''
})

// 还书表单
const returnForm = reactive({ bookBarcode: '' })

// 校验借书证
const checkCard = async () => {
  if (!borrowForm.cardNo) {
    return ElMessage.warning('请输入借书证号')
  }
  cardLoading.value = true
  try {
    const res = await borrowApi.checkCard(borrowForm.cardNo)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      cardValid.value = false
      return
    }
    if (!res.data.canBorrow) {
      ElMessage.error(res.msg)
      cardValid.value = false
      return
    }
    cardValid.value = true
    studentDialog.data = res.data
    studentDialog.visible = true
  } catch (err) {
    console.error("校验证件异常：", err)
    ElMessage.error('请求失败，请检查后端服务')
  } finally {
    cardLoading.value = false
  }
}

// 根据【图书条码】查询图书（对接后端getBookByBarCode接口）
const searchBook = async () => {
  if (!borrowForm.barCode) {
    return ElMessage.warning('请输入/扫描图书条码')
  }
  try {
    const res = await borrowApi.getBookByBarCode(borrowForm.barCode)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    const book = res.data
    const exist = borrowList.value.find(item => item.barcode === book.barCode)
    if (exist) {
      return ElMessage.warning('该图书已添加至借阅列表')
    }
    borrowList.value.push({
      barcode: book.barCode,
      isbn: book.ISBN,
      name: book.bname,
      place: book.place
    })
    borrowForm.barCode = ''
    ElMessage.success('图书添加成功')
  } catch (err) {
    console.error("查询图书异常：", err)
    ElMessage.error('查询图书失败，条码不存在或已借出')
  }
}

// 删除列表图书
const delBook = (index) => {
  borrowList.value.splice(index, 1)
}

// 提交借书：传ISBN给后端执行借阅
const submitBorrow = async () => {
  if (borrowList.value.length === 0) {
    return ElMessage.warning('请先添加要借阅的图书')
  }
  submitLoading.value = true
  let successNum = 0
  let failNum = 0

  try {
    const cardNo = borrowForm.cardNo
    for (const item of borrowList.value) {
      const res = await borrowApi.doBorrow({
        cardNo: cardNo,
        ISBN: item.isbn
      })
      if (res.code === 200) {
        successNum++
      } else {
        failNum++
        console.log(`《${item.name}》借阅失败原因：`, res.msg)
      }
    }
    if (successNum > 0 && failNum === 0) {
      ElMessage.success(`全部借阅成功，共${successNum}本`)
    } else if (successNum > 0 && failNum > 0) {
      ElMessage.warning(`成功${successNum}本，${failNum}本借阅失败，失败详情查看控制台`)
    } else {
      ElMessage.error('所有图书借阅失败，请检查图书或借书证状态')
    }
  } catch (error) {
    console.error("借书捕获到真实异常：", error)
    ElMessage.error('借书请求异常，请稍后重试')
  } finally {
    borrowList.value = []
    borrowForm.cardNo = ''
    cardValid.value = false
    submitLoading.value = false
  }
}

// 还书方法
const submitReturn = () => {
  if (!returnForm.bookBarcode) {
    return ElMessage.warning('请输入书籍条码')
  }
  ElMessage.success('还书成功')
  returnForm.bookBarcode = ''
}
</script>

<style scoped>
.borrow-container {
  padding: 20px;
}
</style>