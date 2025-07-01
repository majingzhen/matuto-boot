import { defineStore } from 'pinia'
import http from '@/utils/http'

function getCache(key, def) {
  try {
    return JSON.parse(localStorage.getItem(key)) || def
  } catch {
    return def
  }
}

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: getCache('userInfo', null),
    perms: getCache('perms', []),
    menu: getCache('menu', []),
    isLogin: !!localStorage.getItem('token')
  }),
  actions: {
    setToken(token) {
      this.token = token
      this.isLogin = true
      localStorage.setItem('token', token)
    },
    setUserInfo(info) {
      this.userInfo = info
      localStorage.setItem('userInfo', JSON.stringify(info))
    },
    setPerms(perms) {
      this.perms = perms
      localStorage.setItem('perms', JSON.stringify(perms))
    },
    setMenu(menu) {
      this.menu = menu
      localStorage.setItem('menu', JSON.stringify(menu))
    },
    async fetchUserInfo() {
      if (this.userInfo) return this.userInfo
      const res = await http.get('/auth/user-info')
      this.setUserInfo(res)
      return res
    },
    async fetchUserPerms() {
      if (this.perms.length && this.menu.length) return { perms: this.perms, menu: this.menu }
      const res = await http.get('/auth/user-perms')
      this.setPerms(res.permission || [])
      this.setMenu(res.menu || [])
      return res
    },
    logout() {
      this.token = ''
      this.userInfo = null
      this.perms = []
      this.menu = []
      this.isLogin = false
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      localStorage.removeItem('perms')
      localStorage.removeItem('menu')
    }
  }
}) 