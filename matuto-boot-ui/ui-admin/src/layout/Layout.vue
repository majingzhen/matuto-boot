<script setup>
import { useUserStore } from '@/store/user'
import { useRouter, useRoute } from 'vue-router'
import MenuItem from '@/components/MenuItem.vue'

const userStore = useUserStore()
const router = useRouter()
const route = useRoute()

const systemName = 'Matuto Admin'

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<template>
  <el-container class="layout-root">
    <el-aside width="200px" class="layout-sider">
      <div class="sider-title">{{ systemName }}</div>
      <el-menu :default-active="route.path" router unique-opened>
        <template v-for="item in userStore.menu" :key="item.id">
          <MenuItem :item="item" />
        </template>
      </el-menu>
    </el-aside>
    <el-container direction="vertical">
      <el-header class="layout-header">
        <div class="header-left">{{ systemName }}</div>
        <div class="header-right">
          <el-avatar :size="32" :src="userStore.userInfo?.avatar || ''" style="margin-right:8px;" />
          <span>{{ userStore.userInfo?.nickName || userStore.userInfo?.userName }}</span>
          <el-dropdown>
            <span class="el-dropdown-link">
              <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="layout-content" style="padding:0;width:100%;height:100%;">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.layout-root {
  height: 100vh;
  width: 100vw;
}
.layout-sider {
  background: #001529;
  color: #fff;
  min-height: 100vh;
  box-shadow: 2px 0 8px #f0f1f2;
}
.sider-title {
  font-size: 20px;
  font-weight: bold;
  padding: 24px 0 16px 24px;
  color: #fff;
}
.layout-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  padding: 0 24px;
  height: 64px;
}
.header-left {
  font-size: 18px;
  font-weight: bold;
}
.header-right {
  display: flex;
  align-items: center;
}
.layout-content {
  background: #f5f6fa;
  width: 100%;
  height: calc(100vh - 64px);
  padding: 0;
  box-sizing: border-box;
}
</style> 