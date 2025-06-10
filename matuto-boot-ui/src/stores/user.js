import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref(getToken() || '')
  const userInfo = ref(null)

  /**
   * 登录
   */
  async function loginAction(loginData) {
    try {
      const { data } = await login(loginData)
      token.value = data
      setToken(data)
      return Promise.resolve()
    } catch (error) {
      return Promise.reject(error)
    }
  }

  /**
   * 登出
   */
  async function logoutAction() {
    try {
      await logout()
      resetState()
      return Promise.resolve()
    } catch (error) {
      return Promise.reject(error)
    }
  }

  /**
   * 获取用户信息
   */
  async function getUserInfoAction() {
    try {
      const { data } = await getInfo()
      console.log(data)
      userInfo.value = data
      return Promise.resolve(data)
    } catch (error) {
      return Promise.reject(error)
    }
  }

  /**
   * 重置状态
   */
  function resetState() {
    token.value = ''
    userInfo.value = null
    removeToken()
  }

  return {
    token,
    userInfo,
    loginAction,
    logoutAction,
    getUserInfoAction,
    resetState
  }
}) 