<template>
  <div id="app">
    <!-- 顶部导航栏：只要不是登录页面就显示，不再依赖登录状态 -->
    <div class="nav" v-if="$route.path !== '/login'">
      <div class="nav-wrap">
        <!-- 左侧图书馆LOGO文字，点击跳转首页 -->
        <router-link to="/home" class="nav-logo">图书馆</router-link>

        <!-- 中间居中导航：纯原生a标签，无el-menu折叠问题 -->
        <div class="menu-center">
          <router-link to="/home" class="nav-item">首页</router-link>
          <router-link to="/card" class="nav-item">证件办理</router-link>
          <router-link to="/borrow" class="nav-item">流通部</router-link>
          <router-link to="/catalog" class="nav-item">采编部</router-link>
        </div>

        <!-- 右侧：已登录显示个人中心+退出，未登录显示登录 -->
        <div class="menu-right">
          <div v-if="isLogin" class="user-box">
            <router-link to="/profile" class="nav-item">个人中心</router-link>
            <span class="logout-btn" @click="logout">退出登录</span>
          </div>
          <router-link v-else to="/login" class="nav-item">登录</router-link>
        </div>
      </div>
    </div>

    <!-- 路由出口 -->
    <div class="main">
      <router-view />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const isLogin = ref(false)

// 检查登录状态
const checkLogin = () => {
  isLogin.value = localStorage.getItem('isLogin') === 'true'
}

// 退出登录（修复：清除本地存储后立刻把isLogin置false，按钮即时消失）
const logout = () => {
  localStorage.removeItem('isLogin')
  // 关键修复：手动更新登录状态，不用等路由监听
  isLogin.value = false
  ElMessage.success('已退出登录')
  router.push('/home')
}

onMounted(() => {
  checkLogin()
})

// 监听路由变化刷新登录状态
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
/* 左侧logo禁止压缩 */
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
/* 中间导航区域自适应居中 */
.menu-center {
  flex: 1;
  display: flex;
  justify-content: center;
  gap: 36px;
}
/* 右侧登录固定靠右，禁止压缩 */
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
/* 导航链接样式，复刻el-menu视觉效果 */
.nav-item {
  font-size: 18px;
  color: #303133;
  text-decoration: none;
  padding: 0 4px;
  line-height: 60px;
  position: relative;
}
/* 路由激活下划线 */
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
/* 悬浮变色 */
.nav-item:hover {
  color: #409eff;
}
.main {
  padding: 20px;
}
</style>