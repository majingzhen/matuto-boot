// 权限指令 v-permission
import { useUserStore } from '@/stores/user'

export default {
  mounted(el, binding) {
    const { value } = binding
    const userStore = useUserStore()
    const permissions = userStore.userInfo?.permissions || []
    if (value && value instanceof Array && value.length > 0) {
      const hasPermission = permissions.some(p => value.includes(p))
      if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error('请设置操作权限标签值，如 v-permission="[\'sys:user:add\']"')
    }
  }
} 