<!-- src/views/Login.vue -->
<template>
  <div class="login-box">
    <el-card class="login-card">
      <h2 class="title">图书馆管理系统</h2>
      <el-form ref="loginRef" :model="loginForm" label-width="70px">
        <el-form-item label="账号" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="login-btn" @click="handleLogin">登 录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import api from '../api/index.js'

const router = useRouter()
const loginRef = ref(null)

// 登录表单
const loginForm = ref({
  username: '',
  password: ''
})

// 登录方法
const handleLogin = async () => {
  const { username, password } = loginForm.value
  if (!username || !password) {
    return ElMessage.warning('账号和密码不能为空')
  }

  console.log('1. 准备发起请求，地址是：', api.defaults.baseURL + '/login')
  console.log('2. 请求参数：', { username, password })

  try {
    const res = await api.post('/login', { username, password })
    console.log('3. 拿到完整响应：', res)

    // 直接判断 res.code，不用 res.data
    if (res && res.code === 200) {
      ElMessage.success('登录成功')
      localStorage.setItem('isLogin', 'true')
      console.log('4. 已写入登录标识，准备跳转')
      setTimeout(async () => {
        await router.replace('/home')
        console.log('5. 执行完跳转，当前路径：', router.currentRoute.value.path)
      }, 200)
    } else {
      ElMessage.error(res?.msg || '登录失败')
    }
  } catch (err) {
    console.error('❌ 请求异常，完整错误信息：', err)
    ElMessage.error('服务器连接失败，请检查后端是否启动')
  }
}
</script>

<style scoped>
.login-box {
  width: 100vw;
  height: 100vh;
  background: #f5f7fa;
  display: flex;
  justify-content: center;
  align-items: center;
}
.login-card {
  width: 380px;
  padding: 20px;
}
.title {
  text-align: center;
  margin-bottom: 25px;
  color: #333;
}
.login-btn {
  width: 100%;
}
</style>