import router from './index'
import { useUserStore } from '@/stores/user'
import { useMenuStore } from '@/stores/menu'
import { getToken } from '@/utils/auth'
import { ElMessage } from 'element-plus'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

const whiteList = ['/login']
let hasUserInfo = false

router.beforeEach(async (to, from, next) => {
  NProgress.start()
  
  const hasToken = getToken()
  
  if (hasToken) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      const userStore = useUserStore()
      const menuStore = useMenuStore()
      
      if (userStore.userInfo || hasUserInfo) {
        next()
      } else {
        try {
          // 获取用户信息
          await userStore.getUserInfoAction()
          hasUserInfo = true
          // 获取用户菜单
          const accessRoutes = await menuStore.getMenusAction()
          // 动态添加路由
          accessRoutes.forEach(route => {
            router.addRoute(route)
          })
          next({ ...to, replace: true })
        } catch (error) {
          // 清除用户信息
          await userStore.logoutAction()
          hasUserInfo = false
          ElMessage.error(error.message || '获取用户信息失败')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
}) 