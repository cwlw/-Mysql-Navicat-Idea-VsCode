<!-- src/components/CommonTable.vue -->
<template>
  <el-table :data="data" border stripe style="width: 100%">
    <el-table-column
      v-for="col in columns"
      :key="col.prop"
      :prop="col.prop"
      :label="col.label"
      :width="col.width"
    />
  </el-table>
</template>

<script setup>
// 接收父组件传入的列配置和表格数据
defineProps({
  columns: {
    type: Array,
    required: true
  },
  data: {
    type: Array,
    required: true
  }
})
</script>