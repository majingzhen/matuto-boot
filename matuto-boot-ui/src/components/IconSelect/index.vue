<template>
  <div class="icon-body">
    <el-input v-model="iconName" placeholder="请输入图标名称" clearable @clear="filterIcons" @input="filterIcons">
      <template #prefix>
        <el-icon><Search /></el-icon>
      </template>
    </el-input>
    <div class="icon-list">
      <div v-for="(item, index) in iconList" :key="index" @click="selectedIcon(item)">
        <el-icon :class="{ active: item === activeIcon }">
          <component :is="item" />
        </el-icon>
        <span>{{ item }}</span>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed } from 'vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { Search } from '@element-plus/icons-vue'

const props = defineProps({
  activeIcon: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['selected'])

const iconName = ref('')
const allIcons = Object.keys(ElementPlusIconsVue)

const iconList = computed(() => {
  if (!iconName.value) {
    return allIcons
  }
  return allIcons.filter(name => name.toLowerCase().includes(iconName.value.toLowerCase()))
})

const filterIcons = () => {
  // 图标过滤逻辑已通过计算属性实现
}

const selectedIcon = (name: string) => {
  emit('selected', name)
}

const reset = () => {
  iconName.value = ''
}
</script>

<style lang="scss" scoped>
.icon-body {
  width: 100%;
  padding: 10px;
  .icon-list {
    height: 200px;
    overflow-y: auto;
    margin-top: 10px;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
    gap: 10px;
    
    div {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 85px;
      cursor: pointer;
      border: 1px solid #eee;
      border-radius: 4px;
      transition: all 0.3s;
      
      &:hover {
        background-color: #f5f7fa;
      }
      
      .el-icon {
        font-size: 24px;
        margin-bottom: 8px;
        
        &.active {
          color: var(--el-color-primary);
        }
      }
      
      span {
        font-size: 12px;
        color: #666;
      }
    }
  }
}
</style> 