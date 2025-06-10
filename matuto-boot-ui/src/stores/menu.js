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
        if (!data || !Array.isArray(data)) {
          console.error('菜单数据格式错误:', data)
          return []
        }
        // 生成路由
        this.routes = this.generateRoutes(data)
        return this.routes
      } catch (error) {
        console.error('获取菜单失败:', error)
        return []
      }
    },

    // 生成路由
    generateRoutes(menus) {
      const routes = []
      
      menus.forEach(menu => {
        // 过滤隐藏的菜单
        if (menu.visible === '1') {
          return
        }

        const route = {
          path: menu.path,
          name: menu.menuName,
          component: this.loadComponent(menu.component),
          meta: {
            title: menu.menuName,
            icon: menu.icon,
            perms: menu.perms
          }
        }
        
        if (menu.children && Array.isArray(menu.children) && menu.children.length > 0) {
          route.children = this.generateRoutes(menu.children)
        }
        
        routes.push(route)
      })
      
      return routes
    },

    // 加载组件
    loadComponent(component) {
      if (!component) {
        return () => import('@/layouts/index.vue')
      }
      
      // 处理组件路径
      const componentPath = component.startsWith('/') ? component.slice(1) : component
      return () => import(`@/views/${componentPath}.vue`)
    },

    // 重置状态
    resetState() {
      this.menus = []
      this.routes = []
    }
  }
}) 