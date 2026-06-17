<template>
  <div class="profile-container">
    <el-card title="个人信息">
      <p>姓名：{{ userInfo.name }}</p>
      <p>借书证号：{{ userInfo.cardNo }}</p>
      <p>当前未缴罚款：{{ totalFine }}元</p>
    </el-card>

    <el-divider />
    <el-card title="借书记录">
      <el-table :data="borrowRecords" border>
        <el-table-column prop="barcode" label="条码号" />
        <el-table-column prop="name" label="书名" />
        <el-table-column prop="borrowDate" label="借书日期" />
        <el-table-column prop="dueDate" label="应还日期" />
        <el-table-column prop="status" label="状态" />
      </el-table>
    </el-card>

    <el-divider />
    <el-card title="缴纳罚款">
      <el-table :data="fineRecords" border>
        <el-table-column type="selection" />
        <el-table-column prop="name" label="书名" />
        <el-table-column prop="fine" label="罚款金额" />
        <el-table-column prop="status" label="状态" />
      </el-table>
      <p>已选金额合计：{{ selectedTotal }}元</p>
      <el-button type="primary" @click="payFine">支付</el-button>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const userInfo = ref({ name: '张三', cardNo: 'LIB2023001' })
const borrowRecords = ref([])
const fineRecords = ref([])
const selectedRecords = ref([])

const totalFine = computed(() => {
  return fineRecords.value
    .filter(r => r.status === '未缴纳')
    .reduce((sum, r) => sum + r.fine, 0)
})

const selectedTotal = computed(() => {
  return selectedRecords.value.reduce((sum, r) => sum + r.fine, 0)
})

onMounted(() => {
  borrowRecords.value = [
    { barcode: 'CPY001', name: '深入理解计算机系统', borrowDate: '2025-05-01', dueDate: '2025-05-15', status: '已还' }
  ]
  fineRecords.value = [
    { id: 1, name: '数据结构与算法', fine: 5.0, status: '未缴纳' }
  ]
})

const payFine = () => {
  ElMessage.success(`已缴纳${selectedTotal.value}元罚款`)
}
</script>