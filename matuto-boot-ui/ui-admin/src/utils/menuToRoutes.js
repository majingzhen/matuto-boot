// 预加载所有 views 下的 .vue 文件
const modules = import.meta.glob('/src/views/**/*.vue')

export function menuToRoutes(menu, parentPath = '') {
  const routes = []
  menu.forEach(item => {
    // 递归拼接 fullPath
    item.fullPath = (parentPath ? parentPath + '/' : '/') + item.path
    item.fullPath = item.fullPath.replace(/\\/g, '/')
    if (item.menuType === 'C' && item.component) {
      // 生成相对路径，兼容 glob key
      let compPath = `/src/views/${item.component}.vue`
      if (!modules[compPath]) {
        // 兼容 ./src/views/ 开头
        compPath = `./src/views/${item.component}.vue`
      }
      const comp = modules[compPath]
      if (comp) {
        routes.push({
          path: item.fullPath,
          name: item.menuName,
          component: comp
        })
      }
    }
    if (item.children && item.children.length) {
      routes.push(...menuToRoutes(item.children, item.fullPath))
    }
  })
  return routes
} 