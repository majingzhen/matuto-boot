<template>
  <div class="app-wrapper">
    <!-- 侧边栏 -->
    <div class="sidebar-container">
      <div class="logo">
        <img src="@/assets/icons/logo.svg" alt="logo">
        <span>Matuto Boot</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        :unique-opened="true"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item
          v-for="route in routes"
          :key="route.path"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </div>
    
    <!-- 主容器 -->
    <div class="main-container">
      <!-- 头部 -->
      <div class="navbar">
        <div class="left">
          <el-icon class="hamburger" @click="toggleSidebar">
            <Fold v-if="!isCollapse" />
            <Expand v-else />
          </el-icon>
          <breadcrumb />
        </div>
        <div class="right">
          <el-dropdown trigger="click">
            <div class="avatar-wrapper">
              <el-avatar :size="30" :src="userInfo.avatar" />
              <span class="name">{{ userInfo.nickname }}</span>
              <el-icon><CaretBottom /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleProfile">个人中心</el-dropdown-item>
                <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
      
      <!-- 内容区 -->
      <div class="app-main">
        <router-view v-slot="{ Component }">
          <transition name="fade-transform" mode="out-in">
            <keep-alive>
              <component :is="Component" />
            </keep-alive>
          </transition>
        </router-view>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { useMenuStore } from '@/stores/menu'
import { ElMessageBox } from 'element-plus'
import SidebarItem from './components/SidebarItem.vue'
import Breadcrumb from './components/Breadcrumb.vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const menuStore = useMenuStore()

// 侧边栏折叠状态
const isCollapse = ref(false)
const toggleSidebar = () => {
  isCollapse.value = !isCollapse.value
}

// 当前激活的菜单
const activeMenu = computed(() => {
  return route.path
})

// 路由菜单
const routes = computed(() => {
  return menuStore.routes
})

// 用户信息
const userInfo = computed(() => {
  return userStore.userInfo
})

// 个人中心
const handleProfile = () => {
  router.push('/profile')
}

// 退出登录
const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await userStore.logoutAction()
    router.push('/login')
  })
}
</script>

<style lang="scss" scoped>
.app-wrapper {
  position: relative;
  height: 100vh;
  width: 100%;
  
  .sidebar-container {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    width: 210px;
    background-color: #304156;
    transition: width 0.28s;
    z-index: 1001;
    overflow: hidden;
    
    &.collapse {
      width: 64px;
    }
    
    .logo {
      height: 50px;
      display: flex;
      align-items: center;
      padding: 0 16px;
      color: #fff;
      
      img {
        width: 32px;
        height: 32px;
        margin-right: 12px;
      }
      
      span {
        font-size: 16px;
        font-weight: 600;
      }
    }
  }
  
  .main-container {
    margin-left: 210px;
    min-height: 100%;
    transition: margin-left 0.28s;
    
    .navbar {
      height: 50px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 16px;
      box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
      
      .left {
        display: flex;
        align-items: center;
        
        .hamburger {
          padding: 0 15px;
          cursor: pointer;
          font-size: 20px;
          
          &:hover {
            background: rgba(0, 0, 0, 0.025);
          }
        }
      }
      
      .right {
        .avatar-wrapper {
          display: flex;
          align-items: center;
          cursor: pointer;
          
          .name {
            margin: 0 8px;
          }
        }
      }
    }
    
    .app-main {
      padding: 16px;
      min-height: calc(100vh - 50px);
    }
  }
}

// 路由切换动画
.fade-transform-enter-active,
.fade-transform-leave-active {
  transition: all 0.5s;
}

.fade-transform-enter-from {
  opacity: 0;
  transform: translateX(-30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style> 