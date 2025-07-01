<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/store/user'
import http from '@/utils/http'
import { menuToRoutes } from '@/utils/menuToRoutes'
import router from '@/router'
import Layout from '@/layout/Layout.vue'

const baseURL = import.meta.env.VITE_API_BASE_URL || '/api'

const userStore = useUserStore()

const form = ref({
  username: '',
  password: '',
  captcha: '',
  uuid: '',
  rememberMe: true
})
const captchaUrl = ref('')
const loading = ref(false)

const getCaptcha = () => {
  // 生成 uuid
  form.value.uuid = crypto.randomUUID()
  captchaUrl.value = `${baseURL}/captcha/image?uuid=${form.value.uuid}`
}

const handleLogin = async () => {
  loading.value = true
  try {
    const res = await http.post('/auth/login', form.value)
    userStore.setToken(res)
    await userStore.fetchUserInfo()
    await userStore.fetchUserPerms()
    // 动态注册菜单路由
    const dynamicRoutes = menuToRoutes(userStore.menu)
    dynamicRoutes.forEach(route => {
      router.addRoute({
        path: '/',
        component: Layout,
        children: [route]
      })
    })
    router.push('/')
  } catch (e) {
    getCaptcha() // 登录失败刷新验证码
  } finally {
    loading.value = false
  }
}

getCaptcha()
</script>

<template>
  <div class="login-container">
    <el-form :model="form" @submit.prevent="handleLogin" label-width="80px">
      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="username" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" type="password" autocomplete="current-password" />
      </el-form-item>
      <el-form-item label="验证码">
        <el-row gutter="8">
          <el-col :span="12">
            <el-input v-model="form.captcha" maxlength="6" />
          </el-col>
          <el-col :span="12">
            <img :src="captchaUrl" @click="getCaptcha" style="cursor:pointer;vertical-align:middle;" title="点击刷新验证码" width="100" height="40" />
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="form.rememberMe">记住我</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :loading="loading" @click="handleLogin">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped>
.login-container {
  max-width: 360px;
  margin: 80px auto;
  padding: 32px 24px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.08);
}
</style> 