// 用户信息
export interface UserInfo {
  id: string
  username: string
  nickname: string
  avatar: string
  email?: string
  phone?: string
  roles: string[]
  permissions: string[]
  createTime: string
  updateTime: string
}

// 登录参数
export interface LoginParams {
  username: string
  password: string
  captcha?: string
  captchaId?: string
}

// 登录响应
export interface LoginResult {
  token: string
  userInfo: UserInfo
}

// 修改密码参数
export interface ChangePasswordParams {
  oldPassword: string
  newPassword: string
}

// 更新用户信息参数
export interface UpdateUserInfoParams {
  nickname?: string
  avatar?: string
  email?: string
  phone?: string
} 