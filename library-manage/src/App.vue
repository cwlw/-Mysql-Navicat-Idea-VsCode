<template>
  <div id="app">
    <!-- 顶部导航栏：只要不是登录页面就显示 -->
    <div class="nav" v-if="$route.path !== '/login'">
      <div class="nav-wrap">
        <!-- 左侧LOGO -->
        <router-link to="/visitor" class="nav-logo">图书馆</router-link>

        <!-- 中间导航 -->
        <div class="menu-center">
          <!-- 游客页跳visitor，登录后跳正式home -->
          <router-link 
            :to="isVisitor ? '/visitor' : '/home'" 
            class="nav-item"
          >首页</router-link>
          <!-- 仅登录+管理员+非游客页才显示管理菜单 -->
          <template v-if="isLogin && isAdmin && !isVisitor">
            <router-link to="/card" class="nav-item">证件办理</router-link>
            <router-link to="/borrow" class="nav-item">流通部</router-link>
            <router-link to="/catalog" class="nav-item">采编部</router-link>
          </template>
        </div>

        <!-- 右侧登录/个人中心 -->
        <div class="menu-right">
          <div v-if="isLogin" class="user-box">
            <!-- 修改这里：动态跳转个人中心 -->
            <router-link :to="isAdmin ? '/admin/profile' : '/profile'" class="nav-item">个人中心</router-link>
            <span class="logout-btn" @click="logout">退出登录</span>
          </div>
          <router-link v-else to="/login" class="nav-item">登录</router-link>
        </div>
      </div>
    </div>

    <!-- 路由页面出口 -->
    <div class="main">
      <router-view />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { authApi } from './api/index.js'

const router = useRouter()
const route = useRoute()
const isLogin = ref(false)
const userType = ref('')

// 判断当前是否游客页面
const isVisitor = computed(() => route.path === '/visitor')
// 是否管理员
const isAdmin = computed(() => userType.value === 'admin')

// 读取本地登录状态
const checkLogin = () => {
  isLogin.value = localStorage.getItem('isLogin') === '1'
  userType.value = localStorage.getItem('userType') || ''
}

// 全局刷新登录状态，登录成功调用
window.refreshUserStatus = checkLogin

// 退出登录逻辑
const logout = async () => {
  try {
    await authApi.logout()
  } catch (e) {
    console.log('退出接口请求失败', e)
  }
  // 清空缓存
  localStorage.removeItem('isLogin')
  localStorage.removeItem('userType')
  localStorage.removeItem('loginUserInfo')
  checkLogin()
  ElMessage.success('已退出登录')
  router.push('/visitor')
}

onMounted(() => {
  // 核心：页面初始化强制清空所有登录缓存，打开项目永远是未登录游客状态
  localStorage.removeItem('isLogin')
  localStorage.removeItem('userType')
  localStorage.removeItem('loginUserInfo')
  checkLogin()
})

// 路由切换刷新登录身份
watch(
  () => route.path,
  () => {
    checkLogin()
  }
)
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body {
  background-color: #f5f7fa;
}
.nav {
  background: #fff;
  box-shadow: 0 2px 6px #eee;
}
.nav-wrap {
  display:flex;
  align-items: center;
  width: 100%;
  padding: 0 24px;
  height: 60px;
}
.nav-logo {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
  letter-spacing: 2px;
  flex-shrink: 0;
  user-select: none;
  margin-right: 40px;
  text-decoration: none;
  line-height: 60px;
}
.nav-logo:hover {
  opacity: 0.8;
}
.menu-center {
  flex: 1;
  display: flex;
  justify-content: center;
  gap: 36px;
}
.menu-right {
  flex-shrink: 0;
}
.user-box {
  display: flex;
  align-items: center;
  gap: 20px;
}
.logout-btn {
  font-size: 18px;
  color: #606266;
  cursor: pointer;
  line-height: 60px;
}
.logout-btn:hover {
  color: #409eff;
}
.nav-item {
  font-size: 18px;
  color: #303133;
  text-decoration: none;
  padding: 0 4px;
  line-height: 60px;
  position: relative;
}
.nav-item.router-link-active {
  color: #409eff;
}
.nav-item.router-link-active::after {
  content: '';
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 3px;
  background-color: #409eff;
}
.nav-item:hover {
  color: #409eff;
}
.main {
  padding: 20px;
}
</style>