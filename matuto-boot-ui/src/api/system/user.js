import request from '@/utils/request'

// 查询用户列表
export function getUserList(params) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params
  })
}

// 新增用户
export function addUser(data) {
  return request({
    url: '/system/user',
    method: 'post',
    data
  })
}

// 修改用户
export function updateUser(data) {
  return request({
    url: '/system/user',
    method: 'put',
    data
  })
}

// 删除用户
export function deleteUser(userId) {
  return request({
    url: `/system/user/${userId}`,
    method: 'delete'
  })
}

// 获取用户角色
export function getUserRoles(userId) {
  return request({
    url: `/system/user/role/${userId}`,
    method: 'get'
  })
}

// 更新用户角色
export function updateUserRoles(userId, roleIds) {
  return request({
    url: `/system/user/role/${userId}`,
    method: 'put',
    data: { roleIds }
  })
} 