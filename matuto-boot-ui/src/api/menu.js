import request from '@/utils/request'

/**
 * 获取用户菜单
 */
export function getUserMenus() {
  return request({
    url: '/system/menu/user-menus',
    method: 'get'
  })
} 