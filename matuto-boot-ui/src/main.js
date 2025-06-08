import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import App from './App.vue'
import router from './router'
import './router/permission'
import './styles/index.scss'
import permission from './directive/permission'
import { setupComponents } from './components'

const app = createApp(App)

// 注册 Element Plus 图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 注册权限指令
app.directive('permission', permission)

// 注册全局组件
setupComponents(app)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)

app.mount('#app') 