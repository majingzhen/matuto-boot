/**
 * 登录请求参数
 */
export interface LoginData {
  username: string
  password: string
  captcha: string
  uuid: string
  rememberMe: boolean
}

/**
 * 登录响应数据
 */
export interface LoginResult {
  token: string
}

/**
 * 用户信息
 */
export interface UserInfo {
  userId: number
  username: string
  nickname: string
  avatar?: string
  roles: string[]
  permissions: string[]
} 