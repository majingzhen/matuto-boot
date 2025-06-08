<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-card class="search-wrapper">
      <el-form :model="queryParams" ref="queryForm" :inline="true">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="queryParams.name" placeholder="请输入角色名称" clearable />
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
        <el-button type="primary" @click="handleAdd">新增角色</el-button>
      </template>

      <!-- 表格区域 -->
      <el-table v-loading="loading" :data="roleList">
        <el-table-column type="index" label="序号" width="50" />
        <el-table-column prop="name" label="角色名称" />
        <el-table-column prop="code" label="角色编码" />
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
            <el-button type="primary" link @click="handleMenu(row)">分配菜单</el-button>
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

    <!-- 角色表单对话框 -->
    <el-dialog
      :title="dialog.title"
      v-model="dialog.visible"
      width="500px"
      append-to-body
    >
      <el-form
        ref="roleFormRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入角色编码" />
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

    <!-- 分配菜单对话框 -->
    <el-dialog
      title="分配菜单"
      v-model="menuDialog.visible"
      width="500px"
      append-to-body
    >
      <el-tree
        ref="menuTreeRef"
        :data="menuDialog.menuList"
        :props="{ label: 'name', children: 'children' }"
        show-checkbox
        node-key="id"
        :default-checked-keys="menuDialog.checkedKeys"
      />
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitMenu">确 定</el-button>
          <el-button @click="menuDialog.visible = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getRoleList, addRole, updateRole, deleteRole, getRoleMenus, updateRoleMenus } from '@/api/system/role'
import { listMenu } from '@/api/system/menu'

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  status: undefined
})

// 角色列表数据
const roleList = ref([])
const total = ref(0)
const loading = ref(false)

// 表单对话框
const dialog = reactive({
  visible: false,
  title: '',
  type: ''
})

// 菜单对话框
const menuDialog = reactive({
  visible: false,
  roleId: undefined,
  menuList: [],
  checkedKeys: []
})

// 表单对象
const form = reactive({
  id: undefined,
  name: '',
  code: '',
  status: 1
})

// 表单校验规则
const rules = {
  name: [
    { required: true, message: '请输入角色名称', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入角色编码', trigger: 'blur' }
  ]
}

const roleFormRef = ref()
const menuTreeRef = ref()

// 获取角色列表
const getList = async () => {
  loading.value = true
  try {
    const { data } = await getRoleList(queryParams)
    roleList.value = data.list
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
  queryParams.name = ''
  queryParams.status = undefined
  handleQuery()
}

// 新增按钮操作
const handleAdd = () => {
  dialog.type = 'add'
  dialog.title = '新增角色'
  dialog.visible = true
  Object.assign(form, {
    id: undefined,
    name: '',
    code: '',
    status: 1
  })
}

// 修改按钮操作
const handleUpdate = (row) => {
  dialog.type = 'edit'
  dialog.title = '修改角色'
  dialog.visible = true
  Object.assign(form, row)
}

// 提交表单
const submitForm = async () => {
  if (!roleFormRef.value) return
  await roleFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (dialog.type === 'add') {
          await addRole(form)
          ElMessage.success('新增成功')
        } else {
          await updateRole(form)
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
const handleDelete = (row) => {
  ElMessageBox.confirm('确认要删除该角色吗？', '警告', {
    type: 'warning'
  }).then(async () => {
    await deleteRole(row.id)
    ElMessage.success('删除成功')
    getList()
  })
}

// 分配菜单按钮操作
const handleMenu = async (row) => {
  menuDialog.roleId = row.id
  menuDialog.visible = true
  // 获取所有菜单
  const { data: menuList } = await listMenu()
  menuDialog.menuList = menuList
  // 获取角色菜单
  const { data: roleMenus } = await getRoleMenus(row.id)
  menuDialog.checkedKeys = roleMenus.map(menu => menu.id)
}

// 提交菜单分配
const submitMenu = async () => {
  const menuIds = menuTreeRef.value.getCheckedKeys()
  await updateRoleMenus(menuDialog.roleId, menuIds)
  ElMessage.success('分配菜单成功')
  menuDialog.visible = false
}

// 取消按钮
const cancel = () => {
  dialog.visible = false
  roleFormRef.value?.resetFields()
}

// 分页大小改变
const handleSizeChange = (val) => {
  queryParams.pageSize = val
  getList()
}

// 页码改变
const handleCurrentChange = (val) => {
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