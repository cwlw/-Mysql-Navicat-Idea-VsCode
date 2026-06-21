<!-- src/views/Login.vue -->
<template>
  <div class="login-box">
    <el-card class="login-card">
      <h2 class="title">图书馆管理系统</h2>
      <el-form ref="loginRef" :model="loginForm" label-width="70px">
        <el-form-item label="账号" prop="username">
          <el-input v-model="loginForm.username" placeholder="管理员账号/学生学号"></el-input>
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
import { authApi } from '../api/index.js'

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

  try {
    // 后端接口参数为 username
    const res = await authApi.login({
      username: username,
      password: password
    })
    console.log('登录返回数据：', res)

    if (res && res.code === 200) {
      ElMessage.success('登录成功')
      // 读取后端返回的真实身份
      const userType = res.data.userType
      localStorage.setItem('isLogin', '1')
      localStorage.setItem('userType', userType)
      localStorage.setItem('loginUserInfo', JSON.stringify(res.data.userInfo))
      
      setTimeout(() => {
        router.replace('/home')
      }, 200)
    } else {
      ElMessage.error(res?.msg || '账号或密码错误')
    }
  } catch (err) {
    console.error('登录请求异常：', err)
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