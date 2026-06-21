import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:3000/api',
  timeout: 5000,
  withCredentials: true // 关键：跨域携带Cookie，维持Session登录
});

// 请求拦截器
api.interceptors.request.use(config => {
  return config;
});

// 响应拦截器：捕获401未登录自动跳转登录页
api.interceptors.response.use(
  res => res.data,
  err => {
    // 捕获401未登录
    if (err?.response?.data?.code === 401) {
      // 动态导入路由，修复顶层useRouter报错
      import('vue-router').then(({ useRouter }) => {
        const router = useRouter()
        localStorage.removeItem('userType')
        router.push('/login')
      })
    }
    return Promise.reject(err)
  }
);

// 1. 图书
export const bookApi = {
  list: () => api.get('/book/list'),
  getById: (id) => api.get(`/book/get/${id}`),
  add: (data) => api.post('/book/add', data),
  update: (data) => api.post('/book/update', data),
  del: (id) => api.delete(`/book/delete/${id}`)
};

// 2. 图书副本
export const bookcopyApi = {
  list: () => api.get('/bookcopy/list'),
  getByBarCode: (barCode) => api.get(`/bookcopy/get/${barCode}`),
  add: (data) => api.post('/bookcopy/add', data),
  update: (data) => api.post('/bookcopy/update', data),
  del: (barCode) => api.delete(`/bookcopy/delete/${barCode}`)
};

// 3. 图书分类
export const booktypeApi = {
  list: () => api.get('/booktype/list'),
  getById: (id) => api.get(`/booktype/get/${id}`),
  add: (data) => api.post('/booktype/add', data),
  update: (data) => api.post('/booktype/update', data),
  del: (id) => api.delete(`/booktype/delete/${id}`)
};

// 4. 借阅记录
export const borrowrecApi = {
  list: () => api.get('/borrowrec/list'),
  getById: (id) => api.get(`/borrowrec/get/${id}`),
  add: (data) => api.post('/borrowrec/add', data),
  update: (data) => api.post('/borrowrec/update', data),
  del: (id) => api.delete(`/borrowrec/delete/${id}`)
};

// 5. 借阅卡操作记录
export const cardrecApi = {
  list: () => api.get('/cardrec/list'),
  getById: (id) => api.get(`/cardrec/get/${id}`),
  add: (data) => api.post('/cardrec/add', data),
  update: (data) => api.post('/cardrec/update', data),
  del: (id) => api.delete(`/cardrec/delete/${id}`)
};

// 6. 借阅卡
export const libcardApi = {
  list: () => api.get('/libcard/list'),
  getById: (id) => api.get(`/libcard/get/${id}`),
  add: (data) => api.post('/libcard/add', data),
  update: (data) => api.post('/libcard/update', data),
  del: (id) => api.delete(`/libcard/delete/${id}`),
  getBySno: (sno) => api.get('/libcard/getBySno', { params: { sno } }),
  getMaxCardNo: (sno) => api.get('/libcard/maxCardNo', { params: { sno } })
};

// 7. 公告
export const noticeApi = {
  list: () => api.get('/notice/list'),
  getById: (id) => api.get(`/notice/get/${id}`),
  add: (data) => api.post('/notice/add', data),
  update: (data) => api.post('/notice/update', data),
  del: (id) => api.delete(`/notice/delete/${id}`)
};

// 8. 操作日志
export const operationlogApi = {
  list: () => api.get('/operationlog/list'),
  getById: (id) => api.get(`/operationlog/get/${id}`),
  add: (data) => api.post('/operationlog/add', data),
  update: (data) => api.post('/operationlog/update', data),
  del: (id) => api.delete(`/operationlog/delete/${id}`)
};

// 9. 缴费记录
export const payrecApi = {
  list: () => api.get('/payrec/list'),
  getById: (id) => api.get(`/payrec/get/${id}`),
  add: (data) => api.post('/payrec/add', data),
  update: (data) => api.post('/payrec/update', data),
  del: (id) => api.delete(`/payrec/delete/${id}`)
};

// 10. 角色
export const roleApi = {
  list: () => api.get('/role/list'),
  getById: (id) => api.get(`/role/get/${id}`),
  add: (data) => api.post('/role/add', data),
  update: (data) => api.post('/role/update', data),
  del: (id) => api.delete(`/role/delete/${id}`)
};

// 11. 系统规则配置
export const ruleconfigApi = {
  list: () => api.get('/ruleconfig/list'),
  getById: (id) => api.get(`/ruleconfig/get/${id}`),
  add: (data) => api.post('/ruleconfig/add', data),
  update: (data) => api.post('/ruleconfig/update', data),
  del: (id) => api.delete(`/ruleconfig/delete/${id}`)
};

// 12. 学生/读者
export const studentApi = {
  list: () => api.get('/student/list'),
  getById: (id) => api.get(`/student/get/${id}`),
  add: (data) => api.post('/student/add', data),
  update: (data) => api.post('/student/update', data),
  del: (id) => api.delete(`/student/delete/${id}`),
  getBySno: (sno) => api.get('/student/getBySno', { params: { sno } })
};

// ========== 借阅卡业务操作接口（办卡/挂失/补办/注销）==========
export const cardOperateApi = {
  applyCard: (data) => api.post('/cardOperate/apply', data),
  lossCard: (data) => api.post('/cardOperate/loss', data),
  reissueCard: (data) => api.post('/cardOperate/reissue', data),
  cancelCard: (data) => api.post('/cardOperate/cancel', data)
};

// ========== 登录、获取当前用户、退出登录 ==========
export const authApi = {
  login: (form) => api.post('/login', form),
  getCurrentUser: () => api.get('/getCurrentUser'),
  logout: () => api.get('/logout')
}

// ===================== 【新版学生个人中心接口，前端无需传sno，后端Session自动获取】 =====================
// 获取个人主页综合数据（学生信息+借书卡+当前在借图书）
export function getStudentProfile() {
  return api.get('/student/profile')
}

// 获取当前未归还图书列表
export function getBorrow() {
  return api.get('/student/borrow')
}

// 获取未结清罚款列表
export function getUnpaidFine() {
  return api.get('/student/fine/unpaid')
}

// 批量缴纳罚款（只传id数组，不用传学号）
export function payFine(data) {
  return api.post('/student/fine/pay', data)
}

// 分页查询全部历史借阅记录
export function getBorrowHistory(page, size) {
  return api.get('/student/borrow/history', {
    params: { page, size }
  })
}

// 分页查询历史缴费记录
export function getFineRecord(page, size) {
  return api.get('/student/fine/record', {
    params: { page, size }
  })
}

// 修改个人基础信息
export function updateInfo(data) {
  return api.put('/student/profile/update', data)
}

// 修改登录密码
export function updatePwd(data) {
  return api.put('/student/profile/pwd', data)
}

// ===================== 管理员个人中心接口 =====================
export function getAdminProfile() {
  return api.get('/admin/profile')
}
// 新增管理员修改密码接口
export function updateAdminPwd(data) {
  return api.put('/admin/updatePwd', data)
}

// ===================== 借书业务接口（新增条码查询接口） =====================
export const borrowApi = {
  checkCard: (cardNo) => api.get('/borrowrec/checkCard', { params: { cardNo } }),
  getBookByIsbn: (isbn) => api.get('/borrowrec/getBookByIsbn', { params: { isbn } }),
  getBookByBarCode: (barCode) => api.get('/borrowrec/getBookByBarCode', { params: { barCode } }),
  doBorrow: (data) => api.post('/borrowrec/doBorrow', data)
};

// ===================== 采编部新书入库专用接口（新增） =====================
export const catalogueApi = {
  // 图书分页查询
  getBookPage: (params) => api.get('/catalogue/book/page', { params }),
  // 新增图书
  addBook: (data) => api.post('/catalogue/book/add', data),
  // 编辑图书
  updateBook: (data) => api.put('/catalogue/book/update', data),
  // 图书下架
  downBook: (bookId) => api.put(`/catalogue/book/down/${bookId}`),
  // 根据ISBN获取全部副本
  getCopyListByIsbn: (isbn) => api.get(`/catalogue/copy/list/${isbn}`),
  // 新增副本
  addCopy: (data) => api.post('/catalogue/copy/add', data),
  // 注销副本
  cancelCopy: (barCode) => api.put(`/catalogue/copy/cancel/${barCode}`),
  // 扫码提示
  scanTip: () => api.get('/catalogue/scan/tip')
};

// ==================== 新增缴费、批量更新罚款接口（解决导出报错） ====================
export function addPayRec(data) {
  return payrecApi.add(data)
}

export function updateBorrowFineStatus(data) {
  return api.post("/borrowrec/batchUpdateFineStatus", data)
}

export default api;