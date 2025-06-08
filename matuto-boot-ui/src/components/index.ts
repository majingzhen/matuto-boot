import { App } from 'vue'
import SvgIcon from './SvgIcon/index.vue'
import IconSelect from './IconSelect/index.vue'

// 注册全局组件
export function setupComponents(app: App) {
  app.component('SvgIcon', SvgIcon)
  app.component('IconSelect', IconSelect)
} 