<template>
  <div style="padding: 40px 60px; background: #f5f7fa; min-height: calc(100vh - 60px);">
    <!-- 顶部头像+名称 -->
    <div style="text-align: center; margin-bottom: 40px;">
      <el-avatar size="80" icon="User" />
      <h2 style="font-size: 36px; margin: 10px 0 4px;">{{ adminInfo.name || "管理员" }}</h2>
      <p style="color: #666; font-size: 16px;">重庆理工大学</p>
    </div>

    <!-- 账号资料标题分割线 -->
    <div style="margin-bottom: 30px;">
      <h1 style="font-size: 32px; font-weight: 500; margin: 0;">账号资料</h1>
      <div style="height: 1px; background: #ccc; margin-top: 8px;"></div>
    </div>

    <!-- 两栏布局 匹配截图 -->
    <div style="display: grid; grid-template-columns: 1fr 1fr; row-gap: 24px; font-size: 24px;">
      <!-- 账号+修改密码按钮 -->
      <div style="display: flex; align-items: center; gap: 12px;">
        <span>账号：</span>
        <span>{{ adminInfo.username }}</span>
        <el-button text type="success" @click="openPwdDialog">修改密码</el-button>
      </div>
      <!-- 邮箱 -->
      <div style="display: flex; align-items: center; gap: 12px;">
        <span>邮箱：</span>
        <span>{{ adminInfo.email || "" }}</span>
        <el-button v-if="!adminInfo.email" text type="success">添加</el-button>
      </div>
      <!-- 手机号 -->
      <div style="display: flex; align-items: center; gap: 12px;">
        <span>手机号：</span>
        <span>{{ adminInfo.tel || "" }}</span>
        <el-button v-if="!adminInfo.tel" text type="success">添加</el-button>
      </div>
      <div></div>
    </div>

    <!-- 修改密码弹窗 -->
    <el-dialog v-model="pwdDialogVisible" title="修改登录密码" width="480px">
      <el-form :model="pwdForm" label-width="100px">
        <el-form-item label="原密码">
          <el-input v-model="pwdForm.oldPwd" show-password></el-input>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="pwdForm.newPwd" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认新密码">
          <el-input v-model="pwdForm.confirmPwd" show-password></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="pwdDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitUpdatePwd" :loading="pwdLoading">确认修改</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getAdminProfile, updateAdminPwd } from '@/api/index'

const router = useRouter()
const adminInfo = ref({})

// 修改密码弹窗
const pwdDialogVisible = ref(false)
const pwdLoading = ref(false)
const pwdForm = ref({
  oldPwd: "",
  newPwd: "",
  confirmPwd: ""
})

// 打开弹窗
const openPwdDialog = () => {
  pwdForm.value = { oldPwd: "", newPwd: "", confirmPwd: "" }
  pwdDialogVisible.value = true
}

// 提交修改密码
const submitUpdatePwd = async () => {
  const form = pwdForm.value
  if (!form.oldPwd || !form.newPwd || !form.confirmPwd) {
    return ElMessage.warning("所有密码项不能为空")
  }
  if (form.newPwd !== form.confirmPwd) {
    return ElMessage.warning("两次新密码输入不一致")
  }
  pwdLoading.value = true
  try {
    const res = await updateAdminPwd(form)
    if (res.code === 200) {
      ElMessage.success("密码修改成功，请重新登录")
      pwdDialogVisible.value = false
      // 改完密码强制退出
      localStorage.removeItem('isLogin')
      localStorage.removeItem('userType')
      router.push('/login')
    } else {
      ElMessage.error(res.msg)
    }
  } catch (err) {
    ElMessage.error("修改失败，网络异常")
  } finally {
    pwdLoading.value = false
  }
}

// 加载管理员信息
const loadAdminInfo = async () => {
  const res = await getAdminProfile()
  if (res.code === 200) {
    adminInfo.value = res.data
  } else if (res.code === 401) {
    ElMessage.warning('登录已失效，请重新登录')
    router.push('/login')
  } else {
    ElMessage.error(res.msg)
  }
}

onMounted(() => {
  loadAdminInfo()
})
</script>

<style scoped>
:deep(.el-button--text) {
  color: #36b37e;
  font-size: 20px;
  padding: 0;
}
</style>