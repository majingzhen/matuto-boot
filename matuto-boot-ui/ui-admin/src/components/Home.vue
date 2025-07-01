<script setup>
import { useUserStore } from '@/store/user'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()

const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<template>
  <div class="home-container">
    <h2>欢迎回来！</h2>
    <el-button type="danger" @click="handleLogout">退出登录</el-button>
  </div>
</template>

<style scoped>
.home-container {
  max-width: 600px;
  margin: 80px auto;
  padding: 32px 24px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.08);
  text-align: center;
}
</style> 