<template>
  <div class="catalog-container">
    <el-tabs v-model="activeTab">
      <!-- 新书入库 -->
      <el-tab-pane label="新书入库" name="add">
        <el-form :model="bookForm" label-width="80px">
          <el-form-item label="ISBN">
            <el-input v-model="bookForm.isbn" />
          </el-form-item>
          <el-form-item label="书名">
            <el-input v-model="bookForm.name" />
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="bookForm.author" />
          </el-form-item>
          <el-button type="primary" @click="addBook">新增书籍</el-button>
        </el-form>

        <el-divider />
        <el-card title="书籍副本管理">
          <el-table :data="copyList" border>
            <el-table-column prop="barcode" label="条码号" />
            <el-table-column prop="status" label="状态" />
            <el-table-column prop="location" label="位置" />
          </el-table>
          <el-button type="primary" @click="addCopy">新增副本</el-button>
        </el-card>
      </el-tab-pane>

      <!-- 旧书报废/下架 -->
      <el-tab-pane label="旧书报废/下架" name="discard">
        <el-input v-model="discardBarcode" placeholder="输入书籍条码" />
        <el-button type="danger" @click="discardCopy">报废副本</el-button>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

const activeTab = ref('add')
const copyList = ref([])
const discardBarcode = ref('')

const bookForm = reactive({ isbn: '', name: '', author: '' })

const addBook = () => {
  ElMessage.success('书籍信息已添加')
}

const addCopy = () => {
  copyList.value.push({
    barcode: `CPY${Date.now()}`,
    status: '可借',
    location: 'A区-01-01'
  })
  ElMessage.success('副本添加成功')
}

const discardCopy = () => {
  ElMessage.success('副本已报废')
}
</script>