import { defineStore } from 'pinia'
import { ref } from 'vue'
import { asyncRoutes, constantRoutes } from '@/router'

export const usePermissionStore = defineStore('permission', () => {
  const routes = ref([])
  const dynamicRoutes = ref([])

  // 生成路由
  function generateRoutes(roles) {
    let accessedRoutes
    if (roles.includes('admin')) {
      accessedRoutes = asyncRoutes || []
    } else {
      accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
    }
    dynamicRoutes.value = accessedRoutes
    routes.value = constantRoutes.concat(accessedRoutes)
    return accessedRoutes
  }

  // 根据角色过滤路由
  function filterAsyncRoutes(routes, roles) {
    const res = []

    routes.forEach(route => {
      const tmp = { ...route }
      if (hasPermission(roles, tmp)) {
        if (tmp.children) {
          tmp.children = filterAsyncRoutes(tmp.children, roles)
        }
        res.push(tmp)
      }
    })

    return res
  }

  // 判断是否有权限
  function hasPermission(roles, route) {
    if (route.meta && route.meta.roles) {
      return roles.some(role => route.meta.roles.includes(role))
    }
    return true
  }

  return {
    routes,
    dynamicRoutes,
    generateRoutes
  }
}) 