<template>
  <div class="card-manage">
    <el-tabs v-model="activeTab">
      <!-- 1.新办证件 -->
      <el-tab-pane label="新办证件" name="apply">
        <el-form :model="applyForm" label-width="80px" ref="applyRef">
          <!-- 修复：rules前加: -->
          <el-form-item label="学号" prop="stuId" :rules="[{ required: true, message: '请输入学号', trigger: 'blur' }]">
            <el-input v-model="applyForm.stuId" placeholder="输入学生学号" clearable />
            <el-button type="primary" @click="queryStudent" :loading="stuLoading">查询学生</el-button>
          </el-form-item>
          <div v-if="studentInfo" class="info-card">
            <p>姓名：{{ studentInfo.username }}</p>
            <p>学院：{{ studentInfo.collage }} - {{ studentInfo.major }}</p>
            <p>当前证件状态：
              <span :class="studentInfo.hasCard ? 'text-red' : 'text-green'">
                {{ studentInfo.hasCard ? '已办理借阅卡，无法重复办卡' : '未办理，可新办' }}
              </span>
            </p>
            <!-- 修复：rules前加: -->
            <el-form-item label="借书证号" prop="cardNo" :rules="[{ required: true, message: '请填写借阅卡号', trigger: 'blur' }]">
              <el-input v-model="applyForm.cardNo" :disabled="studentInfo.hasCard" placeholder="输入新借阅卡号" />
            </el-form-item>
            <el-button type="primary" @click="submitApply" :disabled="studentInfo.hasCard" :loading="applyLoading">确认办理新卡</el-button>
          </div>
        </el-form>
      </el-tab-pane>

      <!-- 2.挂失证件 -->
      <el-tab-pane label="挂失证件" name="loss">
        <el-form :model="lossForm" label-width="80px" ref="lossRef">
          <el-form-item label="学号" prop="stuId" :rules="[{ required: true, message: '请输入学号', trigger: 'blur' }]">
            <el-input v-model="lossForm.stuId" placeholder="输入学生学号" clearable />
            <el-button type="primary" @click="queryCard" :loading="cardLoading">查询借阅卡</el-button>
          </el-form-item>
          <div v-if="cardInfo" class="info-card">
            <p>姓名：{{ cardInfo.sname }}</p>
            <p>借阅卡号：{{ cardInfo.cardNo }}</p>
            <p>证件状态：{{ getCardStatusText(cardInfo.cardStatus) }}</p>
            <el-button type="warning" @click="submitLoss" :disabled="cardInfo.cardStatus === '2' || cardInfo.cardStatus === '4'" :loading="lossLoading">确认挂失</el-button>
          </div>
        </el-form>
      </el-tab-pane>

      <!-- 3.补办证件 -->
      <el-tab-pane label="补办证件" name="reissue">
        <el-form :model="reissueForm" label-width="80px" ref="reissueRef">
          <el-form-item label="学号" prop="stuId" :rules="[{ required: true, message: '请输入学号', trigger: 'blur' }]">
            <el-input v-model="reissueForm.stuId" placeholder="输入学生学号" clearable />
            <el-button type="primary" @click="queryReissueCard" :loading="reQueryLoading">查询原有卡片</el-button>
          </el-form-item>
          <div v-if="oldCardInfo" class="info-card">
            <p>姓名：{{ oldCardInfo.sname }}</p>
            <p>原借阅卡号：{{ oldCardInfo.cardNo }}</p>
            <p>原卡状态：{{ getCardStatusText(oldCardInfo.cardStatus) }}</p>
            <el-form-item label="新借书证号" prop="newCardNo" :rules="[{ required: true, message: '请填写新卡号', trigger: 'blur' }]">
              <el-input v-model="reissueForm.newCardNo" placeholder="输入补办新卡号" />
            </el-form-item>
            <el-button type="success" @click="submitReissue" :disabled="oldCardInfo.cardStatus !== '2'" :loading="reissueLoading">确认补办新卡</el-button>
          </div>
        </el-form>
      </el-tab-pane>

      <!-- 4.注销证件 -->
      <el-tab-pane label="注销证件" name="cancel">
        <el-form :model="cancelForm" label-width="80px" ref="cancelRef">
          <el-form-item label="学号" prop="stuId" :rules="[{ required: true, message: '请输入学号', trigger: 'blur' }]">
            <el-input v-model="cancelForm.stuId" placeholder="输入学生学号" clearable />
            <el-button type="primary" @click="queryCancelCard" :loading="cancelQueryLoading">查询借阅卡</el-button>
          </el-form-item>
          <div v-if="cancelCardInfo" class="info-card">
            <p>姓名：{{ cancelCardInfo.sname }}</p>
            <p>借阅卡号：{{ cancelCardInfo.cardNo }}</p>
            <p>证件状态：{{ getCardStatusText(cancelCardInfo.cardStatus) }}</p>
            <el-button type="danger" @click="submitCancel" :loading="cancelLoading" :disabled="cancelCardInfo.cardStatus === '4'">确认注销卡片</el-button>
          </div>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { studentApi, libcardApi, cardOperateApi } from '../api/index.js'

// 当前激活tab
const activeTab = ref('apply')

// 加载状态
const stuLoading = ref(false)
const cardLoading = ref(false)
const reQueryLoading = ref(false)
const cancelQueryLoading = ref(false)
const applyLoading = ref(false)
const lossLoading = ref(false)
const reissueLoading = ref(false)
const cancelLoading = ref(false)

// 表单校验实例
const applyRef = ref(null)
const lossRef = ref(null)
const reissueRef = ref(null)
const cancelRef = ref(null)

// 各模块表单数据
const applyForm = reactive({ stuId: '', cardNo: '' })
const lossForm = reactive({ stuId: '' })
const reissueForm = reactive({ stuId: '', newCardNo: '' })
const cancelForm = reactive({ stuId: '' })

// 查询返回数据容器
const studentInfo = ref(null)
const cardInfo = ref(null)
const oldCardInfo = ref(null)
const cancelCardInfo = ref(null)

// 卡片状态映射（后端数字转文字）
const CARD_STATUS = {
  '1': '正常',
  '2': '已挂失',
  '3': '补办完成',
  '4': '已注销'
}
const getCardStatusText = (status) => CARD_STATUS[status] || '未知状态'

// ====================== 新办卡逻辑 ======================
const queryStudent = async () => {
  if (!applyForm.stuId) return ElMessage.warning('请输入学号')
  stuLoading.value = true
  studentInfo.value = null
  applyForm.cardNo = '' // 清空上次卡号
  try {
    const res = await studentApi.getBySno(applyForm.stuId)
    if (res.code === 200) {
      studentInfo.value = res.data
      ElMessage.success('学生信息查询成功')
      // 学生无借阅卡，传学号生成 C+sno+01
      if (!studentInfo.value.hasCard) {
        const maxCardRes = await libcardApi.getMaxCardNo(applyForm.stuId)
        if (maxCardRes.code === 200) {
          applyForm.cardNo = maxCardRes.data
        }
      }
    } else {
      ElMessage.error(res.msg || '未查询到该学生')
    }
  } catch (err) {
    ElMessage.error('查询失败，请检查后端服务')
    console.error(err)
  } finally {
    stuLoading.value = false
  }
}

const submitApply = async () => {
  await applyRef.value.validate()
  if (!studentInfo.value) return ElMessage.warning('请先查询学生信息')
  if (studentInfo.value.hasCard) return ElMessage.error('该学生已办理借阅卡，禁止重复办卡')
  applyLoading.value = true
  try {
    // 修复：正确方法名 applyCard
    const res = await cardOperateApi.applyCard({
      sno: applyForm.stuId,
      cardNo: applyForm.cardNo
    })
    if (res.code === 200) {
      ElMessage.success('借阅卡新办成功！已同步生成操作记录')
      // 清空表单
      applyForm.cardNo = ''
      applyForm.stuId = ''
      studentInfo.value = null
    } else {
      ElMessage.error(res.msg || '办理失败')
    }
  } catch (err) {
    ElMessage.error('提交请求失败')
    console.error(err)
  } finally {
    applyLoading.value = false
  }
}

// ====================== 挂失逻辑 ======================
const queryCard = async () => {
  if (!lossForm.stuId) return ElMessage.warning('请输入学号')
  cardLoading.value = true
  cardInfo.value = null
  try {
    const res = await libcardApi.getBySno(lossForm.stuId)
    if (res.code === 200) {
      cardInfo.value = res.data
      ElMessage.success('卡片信息查询成功')
    } else {
      ElMessage.error(res.msg || '未查询到有效借阅卡')
    }
  } catch (err) {
    ElMessage.error('查询失败')
    console.error(err)
  } finally {
    cardLoading.value = false
  }
}

const submitLoss = async () => {
  await lossRef.value.validate()
  if (!cardInfo.value) return ElMessage.warning('请先查询卡片信息')
  if (cardInfo.value.cardStatus === '2') return ElMessage.error('该卡片已挂失，无需重复操作')
  if (cardInfo.value.cardStatus === '4') return ElMessage.error('已注销卡片不可挂失')

  ElMessageBox.confirm('确认挂失该借阅卡？挂失后将无法借书', '操作提示', {
    type: 'warning'
  }).then(async () => {
    lossLoading.value = true
    try {
      const res = await cardOperateApi.lossCard({
        sno: lossForm.stuId,
        cardNo: cardInfo.value.cardNo
      })
      if (res.code === 200) {
        ElMessage.success('卡片挂失成功，操作记录已保存')
        lossForm.stuId = ''
        cardInfo.value = null
      } else {
        ElMessage.error(res.msg || '挂失失败')
      }
    } catch (err) {
      ElMessage.error('操作请求失败')
      console.error(err)
    } finally {
      lossLoading.value = false
    }
  }).catch(() => {})
}

// ====================== 补办逻辑 ======================
const queryReissueCard = async () => {
  if (!reissueForm.stuId) return ElMessage.warning('请输入学号')
  reQueryLoading.value = true
  oldCardInfo.value = null
  reissueForm.newCardNo = ''
  try {
    const res = await libcardApi.getBySno(reissueForm.stuId)
    if (res.code === 200) {
      oldCardInfo.value = res.data
      if (oldCardInfo.value.cardStatus !== '2') {
        ElMessage.warning('仅挂失状态卡片支持补办')
      } else {
        ElMessage.success('卡片信息查询成功')
        // 传入学号生成新卡号填充
        const maxCardRes = await libcardApi.getMaxCardNo(reissueForm.stuId)
        if (maxCardRes.code === 200) {
          reissueForm.newCardNo = maxCardRes.data
        }
      }
    } else {
      ElMessage.error(res.msg || '未查询到借阅卡')
    }
  } catch (err) {
    ElMessage.error('查询失败')
    console.error(err)
  } finally {
    reQueryLoading.value = false
  }
}

const submitReissue = async () => {
  await reissueRef.value.validate()
  if (!oldCardInfo.value) return ElMessage.warning('请先查询原卡片')
  if (oldCardInfo.value.cardStatus !== '2') return ElMessage.error('仅挂失卡片可补办')

  ElMessageBox.confirm(`确认补办新卡？原卡号【${oldCardInfo.value.cardNo}】将失效`, '操作提示', {
    type: 'info'
  }).then(async () => {
    reissueLoading.value = true
    try {
      const res = await cardOperateApi.reissueCard({
        sno: reissueForm.stuId,
        originCardNo: oldCardInfo.value.cardNo,
        newCardNo: reissueForm.newCardNo
      })
      if (res.code === 200) {
        ElMessage.success('补办新卡成功，流水日志已生成')
        reissueForm.newCardNo = ''
        reissueForm.stuId = ''
        oldCardInfo.value = null
      } else {
        ElMessage.error(res.msg || '补办失败')
      }
    } catch (err) {
      ElMessage.error('操作请求失败')
      console.error(err)
    } finally {
      reissueLoading.value = false
    }
  }).catch(() => {})
}

// ====================== 注销逻辑 ======================
const queryCancelCard = async () => {
  if (!cancelForm.stuId) return ElMessage.warning('请输入学号')
  cancelQueryLoading.value = true
  cancelCardInfo.value = null
  try {
    const res = await libcardApi.getBySno(cancelForm.stuId)
    if (res.code === 200) {
      cancelCardInfo.value = res.data
      ElMessage.success('卡片信息查询成功')
    } else {
      ElMessage.error(res.msg || '未查询到借阅卡')
    }
  } catch (err) {
    ElMessage.error('查询失败')
    console.error(err)
  } finally {
    cancelQueryLoading.value = false
  }
}

const submitCancel = async () => {
  await cancelRef.value.validate()
  if (!cancelCardInfo.value) return ElMessage.warning('请先查询卡片')
  if (cancelCardInfo.value.cardStatus === '4') return ElMessage.error('卡片已注销，无需重复操作')

  ElMessageBox.confirm('注销后卡片永久失效、无法恢复，确认继续？', '危险提示', {
    type: 'error'
  }).then(async () => {
    cancelLoading.value = true
    try {
      const res = await cardOperateApi.cancelCard({
        sno: cancelForm.stuId,
        cardNo: cancelCardInfo.value.cardNo
      })
      if (res.code === 200) {
        ElMessage.success('卡片注销完成，操作记录已归档')
        cancelForm.stuId = ''
        cancelCardInfo.value = null
      } else {
        ElMessage.error(res.msg || '注销失败')
      }
    } catch (err) {
      ElMessage.error('操作请求失败')
      console.error(err)
    } finally {
      cancelLoading.value = false
    }
  }).catch(() => {})
}
</script>

<style scoped>
.info-card {
  margin-top: 12px;
  padding: 12px;
  border: 1px solid #eee;
  border-radius: 6px;
}
.text-red {
  color: #f56c6c;
}
.text-green {
  color: #67c23a;
}
p {
  margin: 6px 0;
}
.el-button {
  margin-top: 8px;
}
</style>