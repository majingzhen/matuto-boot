<template>
  <el-icon :size="size" :color="color">
    <component :is="name" />
  </el-icon>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const props = defineProps<{
  name: string
  size?: number | string
  color?: string
}>()

const icon = computed(() => {
  return ElementPlusIconsVue[props.name as keyof typeof ElementPlusIconsVue]
})
</script>

<style lang="scss" scoped>
.el-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  vertical-align: middle;
}
</style> 