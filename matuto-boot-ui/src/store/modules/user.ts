import { defineStore } from 'pinia'
import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'

interface UserState {
  token: string
  name: string
  avatar: string
  roles: string[]
  permissions: string[]
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    permissions: []
  }),
  actions: {
    // 登录
    login(userInfo: { username: string; password: string; code: string; uuid: string }) {
      const { username, password, code, uuid } = userInfo
      return new Promise((resolve, reject) => {
        login({ username: username.trim(), password, code, uuid }).then(res => {
          setToken(res.token)
          this.token = res.token
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取用户信息
    getInfo() {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
          const user = res.user
          this.name = user.userName
          this.avatar = user.avatar
          this.roles = user.roles
          this.permissions = user.permissions
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 退出系统
    logOut() {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          this.token = ''
          this.roles = []
          this.permissions = []
          removeToken()
          resolve(null)
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}) 