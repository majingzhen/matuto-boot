import type { RouteRecordRaw } from 'vue-router'

/**
 * 菜单项
 */
export interface MenuItem {
  id: number
  parentId: number
  name: string
  path: string
  component: string
  perms: string[] // 权限标识
  type: 'M' | 'C' | 'F'  // M:目录 C:菜单 F:按钮
  icon?: string
  orderNum: number
  status: number
  children?: MenuItem[]
}

/**
 * 路由项
 */
export interface AppRouteRecordRaw extends Omit<RouteRecordRaw, 'children'> {
  name: string
  meta: {
    title: string
    icon?: string
    noCache?: boolean
    link?: string
    hidden?: boolean
    roles?: string[]
    perms?: string[]
  }
  children?: AppRouteRecordRaw[]
} 