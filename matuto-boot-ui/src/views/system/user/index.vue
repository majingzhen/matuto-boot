<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-card class="search-wrapper">
      <el-form :model="queryParams" ref="queryForm" :inline="true">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="queryParams.username" placeholder="请输入用户名" clearable />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="queryParams.phone" placeholder="请输入手机号" clearable />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="正常" :value="1" />
            <el-option label="停用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">搜索</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作按钮区域 -->
    <el-card class="table-wrapper">
      <template #header>
        <el-button type="primary" @click="handleAdd">新增用户</el-button>
      </template>

      <!-- 表格区域 -->
      <el-table v-loading="loading" :data="userList">
        <el-table-column type="index" label="序号" width="50" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="nickname" label="昵称" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '正常' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleUpdate(row)">编辑</el-button>
            <el-button type="primary" link @click="handleRole(row)">分配角色</el-button>
            <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination
        v-model:current-page="queryParams.pageNum"
        v-model:page-size="queryParams.pageSize"
        :total="total"
        :page-sizes="[10, 20, 30, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>

    <!-- 用户表单对话框 -->
    <el-dialog
      :title="dialog.title"
      v-model="dialog.visible"
      width="500px"
      append-to-body
    >
      <el-form
        ref="userFormRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="dialog.type === 'add'">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">正常</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 分配角色对话框 -->
    <el-dialog
      title="分配角色"
      v-model="roleDialog.visible"
      width="500px"
      append-to-body
    >
      <el-form label-width="80px">
        <el-form-item label="角色">
          <el-checkbox-group v-model="roleDialog.roleIds">
            <el-checkbox
              v-for="role in roleDialog.roleList"
              :key="role.id"
              :label="role.id"
            >
              {{ role.name }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitRole">确 定</el-button>
          <el-button @click="roleDialog.visible = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance } from 'element-plus'
import { getUserList, addUser, updateUser, deleteUser, getUserRoles, updateUserRoles } from '@/api/system/user'
import { getRoleList } from '@/api/system/role'

interface QueryParams {
  pageNum: number
  pageSize: number
  username: string
  phone: string
  status: number | undefined
}

interface UserForm {
  id: number | undefined
  username: string
  nickname: string
  phone: string
  email: string
  password: string
  status: number
}

interface RoleDialog {
  visible: boolean
  userId: number | undefined
  roleIds: number[]
  roleList: any[]
}

// 查询参数
const queryParams = reactive<QueryParams>({
  pageNum: 1,
  pageSize: 10,
  username: '',
  phone: '',
  status: undefined
})

// 用户列表数据
const userList = ref<any[]>([])
const total = ref(0)
const loading = ref(false)

// 表单对话框
const dialog = reactive({
  visible: false,
  title: '',
  type: ''
})

// 角色对话框
const roleDialog = reactive<RoleDialog>({
  visible: false,
  userId: undefined,
  roleIds: [],
  roleList: []
})

// 表单对象
const form = reactive<UserForm>({
  id: undefined,
  username: '',
  nickname: '',
  phone: '',
  email: '',
  password: '',
  status: 1
})

// 表单校验规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ]
}

const userFormRef = ref<FormInstance>()

// 获取用户列表
const getList = async () => {
  loading.value = true
  try {
    const { data } = await getUserList(queryParams)
    userList.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

// 搜索按钮操作
const handleQuery = () => {
  queryParams.pageNum = 1
  getList()
}

// 重置按钮操作
const resetQuery = () => {
  queryParams.username = ''
  queryParams.phone = ''
  queryParams.status = undefined
  handleQuery()
}

// 新增按钮操作
const handleAdd = () => {
  dialog.type = 'add'
  dialog.title = '新增用户'
  dialog.visible = true
  Object.assign(form, {
    id: undefined,
    username: '',
    nickname: '',
    phone: '',
    email: '',
    password: '',
    status: 1
  })
}

// 修改按钮操作
const handleUpdate = (row: any) => {
  dialog.type = 'edit'
  dialog.title = '修改用户'
  dialog.visible = true
  Object.assign(form, row)
}

// 提交表单
const submitForm = async () => {
  if (!userFormRef.value) return
  await userFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (dialog.type === 'add') {
          await addUser(form)
          ElMessage.success('新增成功')
        } else {
          await updateUser(form)
          ElMessage.success('修改成功')
        }
        dialog.visible = false
        getList()
      } catch (error) {
        console.error(error)
      }
    }
  })
}

// 删除按钮操作
const handleDelete = (row: any) => {
  ElMessageBox.confirm('确认要删除该用户吗？', '警告', {
    type: 'warning'
  }).then(async () => {
    await deleteUser(row.id)
    ElMessage.success('删除成功')
    getList()
  })
}

// 分配角色按钮操作
const handleRole = async (row: any) => {
  roleDialog.userId = row.id
  roleDialog.visible = true
  // 获取所有角色
  const { data: roleList } = await getRoleList()
  roleDialog.roleList = roleList
  // 获取用户角色
  const { data: userRoles } = await getUserRoles(row.id)
  roleDialog.roleIds = userRoles.map(role => role.id)
}

// 提交角色分配
const submitRole = async () => {
  if (!roleDialog.userId) return
  await updateUserRoles(roleDialog.userId, roleDialog.roleIds)
  ElMessage.success('分配角色成功')
  roleDialog.visible = false
}

// 取消按钮
const cancel = () => {
  dialog.visible = false
  userFormRef.value?.resetFields()
}

// 分页大小改变
const handleSizeChange = (val: number) => {
  queryParams.pageSize = val
  getList()
}

// 页码改变
const handleCurrentChange = (val: number) => {
  queryParams.pageNum = val
  getList()
}

onMounted(() => {
  getList()
})
</script>

<style scoped>
.search-wrapper {
  margin-bottom: 20px;
}
.table-wrapper {
  margin-bottom: 20px;
}
.dialog-footer {
  text-align: right;
}
</style> 