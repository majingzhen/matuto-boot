import request from '@/utils/request'

// 查询角色列表
export function getRoleList() {
  return request({
    url: '/system/role/list',
    method: 'get'
  })
}

// 新增角色
export function addRole(data) {
  return request({
    url: '/system/role',
    method: 'post',
    data
  })
}

// 修改角色
export function updateRole(data) {
  return request({
    url: '/system/role',
    method: 'put',
    data
  })
}

// 删除角色
export function deleteRole(roleId) {
  return request({
    url: `/system/role/${roleId}`,
    method: 'delete'
  })
}

// 获取角色菜单
export function getRoleMenus(roleId) {
  return request({
    url: `/system/role/menu/${roleId}`,
    method: 'get'
  })
}

// 更新角色菜单
export function updateRoleMenus(roleId, menuIds) {
  return request({
    url: `/system/role/menu/${roleId}`,
    method: 'put',
    data: { menuIds }
  })
} 