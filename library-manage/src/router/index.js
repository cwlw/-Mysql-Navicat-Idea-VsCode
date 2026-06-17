import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  // 根路径直接跳转首页
  { path: '/', redirect: '/home' },
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  { path: '/home', name: 'Home', component: () => import('../views/Home.vue') },
  // 全部图书页面
  { path: '/allBook', name: 'AllBook', component: () => import('../views/AllBook.vue') },
  // 图书详情页面
  { path: '/bookDetail', name: 'BookDetail', component: () => import('../views/BookDetail.vue') },
  // 证件办理
  { path: '/card', name: 'CardManage', component: () => import('../views/CardManage.vue') },
  // 流通部
  { path: '/borrow', name: 'Borrow', component: () => import('../views/Borrow.vue') },
  // 采编部
  { path: '/catalog', name: 'Catalog', component: () => import('../views/Catalog.vue') },
  // 原个人中心页面（导航栏已统一合并为登录入口，此路由保留供内部跳转）
  { path: '/profile', name: 'Profile', component: () => import('../views/Profile.vue') },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router