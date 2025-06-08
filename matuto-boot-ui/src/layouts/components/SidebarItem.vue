<template>
  <div v-if="!item.hidden">
    <!-- 没有子菜单 -->
    <template v-if="!hasOneShowingChild(item.children, item) || onlyOneChild.children">
      <el-sub-menu :index="resolvePath(item.path)">
        <template #title>
          <el-icon v-if="item.meta?.icon">
            <component :is="item.meta.icon" />
          </el-icon>
          <span>{{ item.meta?.title }}</span>
        </template>
        <sidebar-item
          v-for="child in item.children"
          :key="child.path"
          :item="child"
          :base-path="resolvePath(child.path)"
        />
      </el-sub-menu>
    </template>
    
    <!-- 有子菜单 -->
    <el-menu-item v-else :index="resolvePath(onlyOneChild.path)">
      <el-icon v-if="onlyOneChild.meta?.icon">
        <component :is="onlyOneChild.meta.icon" />
      </el-icon>
      <template #title>{{ onlyOneChild.meta?.title }}</template>
    </el-menu-item>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { isExternal } from '@/utils/validate'
import path from 'path-browserify'
import type { AppRouteRecordRaw } from '@/types/route'

const props = defineProps<{
  item: AppRouteRecordRaw
  basePath: string
}>()

const onlyOneChild = ref<AppRouteRecordRaw>()

// 判断是否只有一个子菜单
const hasOneShowingChild = (children: AppRouteRecordRaw[] = [], parent: AppRouteRecordRaw) => {
  if (!children) {
    children = []
  }
  
  const showingChildren = children.filter(item => {
    if (item.hidden) {
      return false
    } else {
      // 临时设置
      onlyOneChild.value = item
      return true
    }
  })
  
  // 当只有一个子路由时，默认显示子路由
  if (showingChildren.length === 1) {
    return true
  }
  
  // 没有子路由则显示父路由
  if (showingChildren.length === 0) {
    onlyOneChild.value = { ...parent, path: '' }
    return true
  }
  
  return false
}

// 解析路径
const resolvePath = (routePath: string) => {
  if (isExternal(routePath)) {
    return routePath
  }
  if (isExternal(props.basePath)) {
    return props.basePath
  }
  return path.resolve(props.basePath, routePath)
}
</script> 