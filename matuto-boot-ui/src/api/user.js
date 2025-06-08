import { get, post } from '@/utils/request'

// 用户登录
export function login(data) {
  return post('/auth/login', data)
}

// 获取用户信息
export function getInfo() {
  return get('/user/info')
}

// 用户登出
export function logout() {
  return post('/auth/logout')
}

// 修改密码
export function changePassword(data) {
  return post('/user/password', data)
}

// 更新用户信息
export function updateUserInfo(data) {
  return post('/user/info', data)
} 