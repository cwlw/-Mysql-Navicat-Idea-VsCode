import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:3000/api',
  timeout: 5000
});

// 请求拦截器
api.interceptors.request.use(config => {
  return config;
});

// 响应拦截器：直接取出后端 data 数据
api.interceptors.response.use(
  res => res.data,
  err => Promise.reject(err)
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
  // 根据学号查询借阅卡
  getBySno: (sno) => api.get('/libcard/getBySno', { params: { sno } }),
  // 新增：传入学号，自动生成 C+学号+01 卡号
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
  // 根据学号查询学生（附带是否办卡标识）
  getBySno: (sno) => api.get('/student/getBySno', { params: { sno } })
};

// ========== 借阅卡业务操作接口（办卡/挂失/补办/注销）==========
export const cardOperateApi = {
  // 新办借阅卡
  applyCard: (data) => api.post('/cardOperate/apply', data),
  // 挂失借阅卡
  lossCard: (data) => api.post('/cardOperate/loss', data),
  // 补办借阅卡
  reissueCard: (data) => api.post('/cardOperate/reissue', data),
  // 注销借阅卡
  cancelCard: (data) => api.post('/cardOperate/cancel', data)
};

export default api;