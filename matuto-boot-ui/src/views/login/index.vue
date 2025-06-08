<template>
  <div class="login-container">
    <el-form
      ref="loginFormRef"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <div class="title-container">
        <h3 class="title">Matuto Boot</h3>
      </div>

      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          placeholder="用户名"
          type="text"
          tabindex="1"
          auto-complete="on"
        >
          <template #prefix>
            <el-icon><User /></el-icon>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          :type="passwordVisible ? 'text' : 'password'"
          placeholder="密码"
          tabindex="2"
          auto-complete="on"
        >
          <template #prefix>
            <el-icon><Lock /></el-icon>
          </template>
          <template #suffix>
            <el-icon
              class="cursor-pointer"
              @click="passwordVisible = !passwordVisible"
            >
              <View v-if="passwordVisible" />
              <Hide v-else />
            </el-icon>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="captcha">
        <div class="captcha-container">
          <el-input
            v-model="loginForm.captcha"
            placeholder="验证码"
            tabindex="3"
            auto-complete="off"
          >
            <template #prefix>
              <el-icon><Key /></el-icon>
            </template>
          </el-input>
          <img
            :src="captchaUrl"
            class="captcha-img"
            @click="refreshCaptcha"
            alt="验证码"
          />
        </div>
      </el-form-item>

      <el-form-item>
        <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
      </el-form-item>

      <el-button
        :loading="loading"
        type="primary"
        class="w-full"
        @click="handleLogin"
      >
        登录
      </el-button>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock, View, Hide, Key } from '@element-plus/icons-vue'
import type { FormInstance } from 'element-plus'
import { useUserStore } from '@/stores/user'
import type { LoginData } from '@/types/auth'

const router = useRouter()
const userStore = useUserStore()

const loginFormRef = ref<FormInstance>()
const loading = ref(false)
const passwordVisible = ref(false)
const captchaUrl = ref('/api/captcha')

const loginForm = reactive<LoginData>({
  username: '',
  password: '',
  captcha: '',
  uuid: '',
  rememberMe: false
})

const loginRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  captcha: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
}

/**
 * 刷新验证码
 */
function refreshCaptcha() {
  captchaUrl.value = `/api/captcha/image?t=${new Date().getTime()}`
}

/**
 * 登录
 */
async function handleLogin() {
  if (!loginFormRef.value) return
  
  try {
    await loginFormRef.value.validate()
    loading.value = true
    
    // 设置验证码标识
    loginForm.uuid = captchaUrl.value.split('=')[1]
    
    await userStore.loginAction(loginForm)
    await userStore.getUserInfoAction()
    
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error: any) {
    ElMessage.error(error.message || '登录失败')
    refreshCaptcha()
  } finally {
    loading.value = false
  }
}

// 初始化验证码
refreshCaptcha()
</script>

<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
  
  .login-form {
    width: 400px;
    padding: 35px;
    border-radius: 6px;
    background: #fff;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    
    .title-container {
      text-align: center;
      margin-bottom: 30px;
      
      .title {
        font-size: 26px;
        color: #333;
        margin: 0;
      }
    }
    
    .captcha-container {
      display: flex;
      align-items: center;
      gap: 10px;
      
      .captcha-img {
        height: 40px;
        cursor: pointer;
      }
    }
  }
}
</style> 