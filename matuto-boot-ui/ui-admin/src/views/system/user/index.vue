<script setup>
import { ref, reactive, onMounted } from 'vue'
import http from '@/utils/http'
import { ElMessage, ElMessageBox } from 'element-plus'

// 查询条件
const query = reactive({
  userName: '',
  nickName: '',
  status: ''
})

// 列表数据
const userList = ref([])
const total = ref(0)
const loading = ref(false)
const page = ref(1)
const pageSize = ref(10)

// 弹窗表单
const dialogVisible = ref(false)
const dialogTitle = ref('')
const form = reactive({
  id: null,
  userName: '',
  nickName: '',
  email: '',
  phone: '',
  gender: '',
  status: '',
  remark: ''
})
const formRules = {
  userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  nickName: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  email: [{ type: 'email', message: '邮箱格式不正确', trigger: 'blur' }],
  phone: [{ pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }]
}
const formRef = ref()

// 角色分配弹窗
const roleDialogVisible = ref(false)
const roleList = ref([])
const checkedRoles = ref([])
const currentUserId = ref(null)

// 查询用户列表
const fetchList = async () => {
  loading.value = true
  try {
    const res = await http.get('/system/user/list', { ...query, page: page.value, pageSize: pageSize.value })
    userList.value = res.records || res.list || []
    total.value = res.total || 0
  } finally {
    loading.value = false
  }
}

// 查询
const handleQuery = () => {
  page.value = 1
  fetchList()
}

// 重置
const handleReset = () => {
  query.userName = ''
  query.nickName = ''
  query.status = ''
  handleQuery()
}

// 新增
const handleAdd = () => {
  dialogTitle.value = '新增用户'
  Object.assign(form, { id: null, userName: '', nickName: '', email: '', phone: '', gender: '', status: '', remark: '' })
  dialogVisible.value = true
}

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = '编辑用户'
  Object.assign(form, row)
  dialogVisible.value = true
}

// 保存
const handleSave = () => {
  formRef.value.validate(async (valid) => {
    if (!valid) return
    try {
      if (form.id) {
        await http.put('/system/user', form)
        ElMessage.success('修改成功')
      } else {
        await http.post('/system/user', form)
        ElMessage.success('新增成功')
      }
      dialogVisible.value = false
      fetchList()
    } catch (e) {}
  })
}

// 删除
const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该用户吗？', '提示', { type: 'warning' })
    .then(async () => {
      await http.delete(`/system/user/${row.id}`)
      ElMessage.success('删除成功')
      fetchList()
    })
}

// 导出
const handleExport = () => {
  // 可根据后端实际导出接口实现，这里仅做前端导出示例
  const csv = userList.value.map(u => `${u.userName},${u.nickName},${u.email},${u.phone},${u.status}`).join('\n')
  const blob = new Blob([csv], { type: 'text/csv' })
  const url = URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = '用户列表.csv'
  a.click()
  URL.revokeObjectURL(url)
}

// 分配角色
const handleRole = async (row) => {
  currentUserId.value = row.id
  const res = await http.get(`/system/user/role/${row.id}`)
  roleList.value = res.allRoles || []
  checkedRoles.value = res.userRoles || []
  roleDialogVisible.value = true
}
const handleRoleSave = async () => {
  await http.put(`/system/user/role/${currentUserId.value}`, checkedRoles.value)
  ElMessage.success('分配角色成功')
  roleDialogVisible.value = false
}

onMounted(fetchList)
</script>

<template>
  <div>
    <!-- 查询表单 -->
    <el-form :inline="true" :model="query" class="mb-2">
      <el-form-item label="用户名">
        <el-input v-model="query.userName" placeholder="用户名" />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="query.nickName" placeholder="昵称" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="query.status" placeholder="全部">
          <el-option label="全部" value="" />
          <el-option label="正常" value="0" />
          <el-option label="禁用" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="handleAdd">新增</el-button>
        <el-button type="info" @click="handleExport">导出</el-button>
      </el-form-item>
    </el-form>

    <!-- 用户表格 -->
    <el-table :data="userList" border v-loading="loading" style="width: 100%">
      <el-table-column prop="userName" label="用户名" />
      <el-table-column prop="nickName" label="昵称" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="status" label="状态">
        <template #default="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
            {{ scope.row.status === '0' ? '正常' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" width="220">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="warning" @click="handleRole(scope.row)">分配角色</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-model:current-page="page"
      v-model:page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next, sizes"
      @current-change="fetchList"
      @size-change="fetchList"
      class="mt-2"
    />

    <!-- 新增/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" :rules="formRules" ref="formRef" label-width="80px">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="form.nickName" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status">
            <el-option label="正常" value="0" />
            <el-option label="禁用" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>

    <!-- 分配角色弹窗 -->
    <el-dialog v-model="roleDialogVisible" title="分配角色" width="400px">
      <el-checkbox-group v-model="checkedRoles">
        <el-checkbox v-for="role in roleList" :key="role.id" :label="role.id">{{ role.name }}</el-checkbox>
      </el-checkbox-group>
      <template #footer>
        <el-button @click="roleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleRoleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template> 