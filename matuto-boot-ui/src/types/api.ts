// API 响应数据接口
export interface ApiResponse<T = any> {
  code: number
  msg: string
  data: T
}

// 分页请求参数接口
export interface PageParams {
  pageNum: number
  pageSize: number
}

// 分页响应数据接口
export interface PageResult<T> {
  total: number
  list: T[]
}

// 登录请求参数接口
export interface LoginParams {
  username: string
  password: string
}

// 登录响应数据接口
export interface LoginResult {
  token: string
  userInfo: UserInfo
}

// 用户信息接口
export interface UserInfo {
  userId: number
  username: string
  nickname: string
  avatar: string
  roles: string[]
  permissions: string[]
} 