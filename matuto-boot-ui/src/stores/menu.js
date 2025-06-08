import { defineStore } from 'pinia'
import { getUserMenus } from '@/api/menu'
import { constantRoutes } from '@/router'

export const useMenuStore = defineStore('menu', {
  state: () => ({
    menus: [],
    routes: []
  }),
  
  actions: {
    // 获取用户菜单
    async getMenusAction() {
      try {
        const { data } = await getUserMenus()
        this.menus = data
        // 生成路由
        this.routes = this.generateRoutes(data)
        return this.routes
      } catch (error) {
        return []
      }
    },

    // 生成路由
    generateRoutes(menus) {
      const routes = []
      
      menus.forEach(menu => {
        const route = {
          path: menu.path,
          name: menu.name,
          component: this.loadComponent(menu.component),
          meta: {
            title: menu.name,
            icon: menu.icon,
            perms: Array.isArray(menu.perms) ? menu.perms : []
          }
        }
        
        if (menu.children && menu.children.length > 0) {
          route.children = this.generateRoutes(menu.children)
        }
        
        routes.push(route)
      })
      
      return routes
    },

    // 加载组件
    loadComponent(component) {
      if (component === 'Layout') {
        return () => import('@/layouts/index.vue')
      }
      return () => import(`@/views/${component}.vue`)
    },

    // 重置状态
    resetState() {
      this.menus = []
      this.routes = []
    }
  }
}) 