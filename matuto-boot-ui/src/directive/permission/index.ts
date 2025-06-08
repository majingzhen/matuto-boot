import { DirectiveBinding } from 'vue'
import { useUserStore } from '@/store/modules/user'

function checkPermission(el: HTMLElement, binding: DirectiveBinding) {
  const { value } = binding
  const userStore = useUserStore()
  const permissions = userStore.permissions

  if (value && value instanceof Array && value.length > 0) {
    const hasPermission = permissions.some(permission => {
      return value.includes(permission)
    })

    if (!hasPermission) {
      el.parentNode && el.parentNode.removeChild(el)
    }
  } else {
    throw new Error('need permissions! Like v-permission="[\'admin\',\'editor\']"')
  }
}

export default {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    checkPermission(el, binding)
  },
  updated(el: HTMLElement, binding: DirectiveBinding) {
    checkPermission(el, binding)
  }
} 