<template>
  <div class="borrow-container">
    <!-- 借书 -->
    <el-card title="借书操作" style="margin-bottom: 20px">
      <el-form :model="borrowForm" label-width="80px">
        <el-form-item label="借书证号">
          <el-input v-model="borrowForm.cardNo" placeholder="输入或扫描借书证号" />
          <el-button type="primary" @click="checkCard">检查证件</el-button>
        </el-form-item>
        <div v-if="cardValid">
          <el-form-item label="书籍条码">
            <el-input v-model="borrowForm.bookBarcode" placeholder="扫描书籍条码" />
            <el-button @click="addBookToList">添加</el-button>
          </el-form-item>
          <el-table :data="borrowList" border>
            <el-table-column prop="barcode" label="条码号" />
            <el-table-column prop="isbn" label="ISBN" />
            <el-table-column prop="name" label="书名" />
          </el-table>
          <el-button type="primary" @click="submitBorrow">确认借书</el-button>
        </div>
      </el-form>
    </el-card>

    <!-- 还书 -->
    <el-card title="还书操作">
      <el-form :model="returnForm" label-width="80px">
        <el-form-item label="书籍条码">
          <el-input v-model="returnForm.bookBarcode" placeholder="扫描书籍条码" />
          <el-button type="primary" @click="submitReturn">确认还书</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

const cardValid = ref(false)
const borrowList = ref([])

const borrowForm = reactive({ cardNo: '', bookBarcode: '' })
const returnForm = reactive({ bookBarcode: '' })

// 检查借书证有效性
const checkCard = () => {
  // 模拟校验：未挂失、未注销、罚款<50
  cardValid.value = true
  ElMessage.success('证件有效，可借书')
}

// 添加书籍到列表
const addBookToList = () => {
  borrowList.value.push({
    barcode: borrowForm.bookBarcode,
    isbn: '9787111638685',
    name: '深入理解计算机系统'
  })
}

// 提交借书
const submitBorrow = () => {
  ElMessage.success(`成功借阅${borrowList.value.length}本图书`)
  borrowList.value = []
}

// 提交还书
const submitReturn = () => {
  ElMessage.success('还书成功')
}
</script>