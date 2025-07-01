<template>
  <el-sub-menu v-if="item.children && item.children.length" :index="item.fullPath">
    <template #title>
      <i :class="`el-icon-${item.icon || 'menu'}`"></i>
      <span>{{ item.menuName }}</span>
    </template>
    <template v-for="child in item.children" :key="child.id">
      <MenuItem :item="child" />
    </template>
  </el-sub-menu>
  <el-menu-item v-else :index="item.fullPath">
    <i :class="`el-icon-${item.icon || 'menu'}`"></i>
    <span>{{ item.menuName }}</span>
  </el-menu-item>
</template>

<script>
export default {
  name: 'MenuItem',
  props: {
    item: {
      type: Object,
      required: true
    }
  },
  components: {
    MenuItem: null // 递归自身，Vue3 自动支持
  }
}
</script> 