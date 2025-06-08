# Matuto Boot UI

## 项目介绍
Matuto Boot UI 是一个基于 Vue 3 + TypeScript + Vite + Element Plus 的后台管理系统前端项目。

## 技术栈
- Vue 3
- TypeScript
- Vite
- Element Plus
- Pinia
- Vue Router
- Axios
- Sass

## 开发环境
- Node.js >= 16.0.0
- npm >= 7.0.0

## 安装依赖
```bash
npm install
```

## 启动开发服务器
```bash
npm run dev
```

## 构建生产环境
```bash
npm run build
```

## 预览生产环境
```bash
npm run preview
```

## 项目结构
```
├── public/              # 静态资源
├── src/                 # 源代码
│   ├── api/            # API 请求
│   ├── assets/         # 主题、字体等静态资源
│   ├── components/     # 全局公用组件
│   ├── layouts/        # 全局布局
│   ├── router/         # 路由
│   ├── stores/         # 状态管理
│   ├── styles/         # 全局样式
│   ├── types/          # 类型定义
│   ├── utils/          # 全局公用方法
│   ├── views/          # 所有页面
│   ├── App.vue         # 入口页面
│   └── main.ts         # 入口文件
├── .env                # 环境变量
├── .env.production     # 生产环境变量
├── .gitignore          # Git 忽略文件
├── index.html          # HTML 模板
├── package.json        # 项目依赖
├── README.md           # 项目说明
├── tsconfig.json       # TypeScript 配置
├── tsconfig.node.json  # Node.js TypeScript 配置
└── vite.config.ts      # Vite 配置
```

## 功能特性
- 登录认证
- 动态路由
- 权限控制
- 主题定制
- 国际化
- 数据可视化
- 富文本编辑器
- Markdown 编辑器
- 代码编辑器
- 文件上传
- 图片裁剪
- 图表展示
- 导出 Excel
- 导出 PDF
- 打印功能
- 拖拽功能
- 复制功能
- 水印功能
- 全屏功能
- 标签页
- 面包屑
- 菜单
- 用户管理
- 角色管理
- 菜单管理
- 部门管理
- 岗位管理
- 字典管理
- 参数设置
- 通知公告
- 日志管理
- 在线用户
- 定时任务
- 服务监控
- 缓存监控
- 系统接口
- 代码生成
- 系统工具
- 个人中心
- 修改密码
- 基本资料
- 我的消息
- 帮助文档
- 关于系统

## 浏览器支持
- Chrome
- Firefox
- Safari
- Edge

## 贡献指南
1. Fork 本仓库
2. 创建你的特性分支 (git checkout -b feature/AmazingFeature)
3. 提交你的改动 (git commit -m 'Add some AmazingFeature')
4. 推送到分支 (git push origin feature/AmazingFeature)
5. 打开一个 Pull Request

## 许可证
[MIT](LICENSE) 