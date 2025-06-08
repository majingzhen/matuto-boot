import { useUserStore } from '@/store/modules/user'

/**
 * 判断用户是否拥有某个权限
 * @param permission 权限标识
 * @returns boolean
 */
export function hasPermission(permission) {
  const userStore = useUserStore()
  return userStore.permissions.includes(permission)
}

/**
 * 判断用户是否拥有某个角色
 * @param role 角色标识
 * @returns boolean
 */
export function hasRole(role) {
  const userStore = useUserStore()
  return userStore.roles.includes(role)
}

/**
 * 判断用户是否拥有任意一个权限
 * @param permissions 权限标识数组
 * @returns boolean
 */
export function hasAnyPermission(permissions) {
  const userStore = useUserStore()
  return permissions.some(permission => userStore.permissions.includes(permission))
}

/**
 * 判断用户是否拥有任意一个角色
 * @param roles 角色标识数组
 * @returns boolean
 */
export function hasAnyRole(roles) {
  const userStore = useUserStore()
  return roles.some(role => userStore.roles.includes(role))
} 