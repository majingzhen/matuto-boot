// 通用 HTTP 封装（基于 axios）
// 适用于 Vue3 + Vite 项目
// 包含请求前处理、鉴权、响应处理，便于二次开发和 context7 智能分析

import axios from 'axios'

// 通过 Vite 环境变量配置 baseURL
const baseURL = import.meta.env.VITE_API_BASE_URL || '/api'

// 创建 axios 实例，可根据需要配置 baseURL
const http = axios.create({
  baseURL,
  timeout: 10000
})

// 请求拦截器：自动加 token，可扩展 loading、参数处理等
http.interceptors.request.use(
  config => {
    // 从 localStorage 获取 token（可根据实际情况修改）
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    // TODO: 可在此处添加全局 loading 处理
    return config
  },
  error => {
    // 请求错误处理
    return Promise.reject(error)
  }
)

// 响应拦截器：统一处理响应和错误
http.interceptors.response.use(
  response => {
    // 统一数据解包，code 200 成功
    if (response.data && response.data.code !== 200) {
      window.$message && window.$message.error(response.data.msg || '请求失败')
      return Promise.reject(response.data)
    }
    return response.data.data
  },
  error => {
    window.$message && window.$message.error(error.message || '网络异常')
    return Promise.reject(error)
  }
)

// 通用请求方法封装
const get = (url, params = {}, config = {}) => http.get(url, { params, ...config })
const post = (url, data = {}, config = {}) => http.post(url, data, config)
const put = (url, data = {}, config = {}) => http.put(url, data, config)
const del = (url, config = {}) => http.delete(url, config)

export default {
  get,
  post,
  put,
  delete: del
} 