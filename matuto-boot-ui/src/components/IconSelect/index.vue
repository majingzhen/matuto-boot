<template>
  <div class="icon-select">
    <el-input
      v-model="iconName"
      placeholder="请输入图标名称"
      clearable
      @clear="handleClear"
    >
      <template #prefix>
        <el-icon><Search /></el-icon>
      </template>
    </el-input>
    <div class="icon-list">
      <div
        v-for="icon in filteredIcons"
        :key="icon"
        class="icon-item"
        @click="handleSelect(icon)"
      >
        <el-icon>
          <component :is="icon" />
        </el-icon>
        <span class="icon-name">{{ icon }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:modelValue'])

const iconName = ref(props.modelValue)
const icons = Object.keys(ElementPlusIconsVue)

const filteredIcons = computed(() => {
  if (!iconName.value) return icons
  return icons.filter(icon => 
    icon.toLowerCase().includes(iconName.value.toLowerCase())
  )
})

const handleSelect = (icon) => {
  iconName.value = icon
  emit('update:modelValue', icon)
}

const handleClear = () => {
  iconName.value = ''
  emit('update:modelValue', '')
}
</script>

<style scoped>
.icon-select {
  width: 100%;
}

.icon-list {
  margin-top: 10px;
  max-height: 300px;
  overflow-y: auto;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
  gap: 10px;
  padding: 10px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}

.icon-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.icon-item:hover {
  background-color: #f5f7fa;
}

.icon-name {
  margin-top: 5px;
  font-size: 12px;
  color: #606266;
}
</style> 