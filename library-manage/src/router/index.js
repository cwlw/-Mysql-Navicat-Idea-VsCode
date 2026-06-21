import { createRouter, createWebHistory } from 'vue-router'
import { ElMessage } from 'element-plus'
import { authApi } from '../api/index.js'

const routes = [
  // 打开项目默认进入游客页面
  { path: '/', redirect: '/visitor' },
  // 游客首页（和学生页面UI完全一致，免登录）
  { path: '/visitor', name: 'Visitor', component: () => import('../views/Home.vue') },
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  { path: '/home', name: 'Home', component: () => import('../views/Home.vue') },
  { path: '/allBook', name: 'AllBook', component: () => import('../views/AllBook.vue') },
  { path: '/bookDetail', name: 'BookDetail', component: () => import('../views/BookDetail.vue') },
  { path: '/card', name: 'CardManage', component: () => import('../views/CardManage.vue') },
  { path: '/borrow', name: 'Borrow', component: () => import('../views/Borrow.vue') },
  { path: '/catalog', name: 'Catalog', component: () => import('../views/Catalog.vue') },
  { path: '/profile', name: 'Profile', component: () => import('../views/Profile.vue') },
  // 管理员个人中心 路径和后端接口路径统一 /admin/profile
  {
    path: '/admin/profile',
    name: 'AdminProfile',
    component: () => import('../views/AdminProfile.vue'),
    meta: { title: '管理员个人中心' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async (to, from, next) => {
  // 新增 /admin/profile 到管理员权限路由列表
  const adminRoutes = ['/card', '/borrow', '/catalog', '/admin/profile']
  // 永久免登录页面：游客页、登录页、全部图书
  const freePages = ['/login', '/visitor', '/allBook', '/bookDetail']

  // 游客页面直接放行，完全不校验登录
  if (freePages.includes(to.path)) {
    next()
    return
  }

  // 剩余页面需要登录校验
  try {
    const res = await authApi.getCurrentUser()
    const userType = res.data.userType
    localStorage.setItem('userType', userType)
    localStorage.setItem('isLogin', '1')

    // 学生拦截管理员专属页面
    if (userType === 'student' && adminRoutes.includes(to.path)) {
      ElMessage.warning('当前账号无权限访问该管理页面')
      next('/home')
      return
    }
    next()
  } catch (err){
    ElMessage.warning('请先登录账号')
    next('/login')
  }
})

export default router