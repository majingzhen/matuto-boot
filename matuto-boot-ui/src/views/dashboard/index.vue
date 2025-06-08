<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>用户总数</span>
            </div>
          </template>
          <div class="card-body">
            <div class="number">{{ statistics.userCount }}</div>
            <div class="icon">
              <el-icon><User /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>角色总数</span>
            </div>
          </template>
          <div class="card-body">
            <div class="number">{{ statistics.roleCount }}</div>
            <div class="icon">
              <el-icon><UserFilled /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>菜单总数</span>
            </div>
          </template>
          <div class="card-body">
            <div class="number">{{ statistics.menuCount }}</div>
            <div class="icon">
              <el-icon><Menu /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>在线用户</span>
            </div>
          </template>
          <div class="card-body">
            <div class="number">{{ statistics.onlineCount }}</div>
            <div class="icon">
              <el-icon><Monitor /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { User, UserFilled, Menu, Monitor } from '@element-plus/icons-vue'

const statistics = ref({
  userCount: 100,
  roleCount: 10,
  menuCount: 50,
  onlineCount: 5
})
</script>

<style lang="scss" scoped>
.dashboard-container {
  padding: 20px;
  
  .el-card {
    margin-bottom: 20px;
    
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    
    .card-body {
      display: flex;
      justify-content: space-between;
      align-items: center;
      
      .number {
        font-size: 24px;
        font-weight: bold;
        color: #303133;
      }
      
      .icon {
        font-size: 48px;
        color: #409EFF;
      }
    }
  }
}
</style> 