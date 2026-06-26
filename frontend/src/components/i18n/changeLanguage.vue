<template>
  <el-dropdown @command="handle">
    <span class="el-dropdown-link">
      {{ $t('Language') }}<i class="el-icon-caret-bottom el-icon--right" />
    </span>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item v-for="(item, index) of list" :key="index" :command="item.key">{{ item.name }}</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  name: 'ChangeLanguage',
  data() {
    return {
      list: [
        { key: 'en', name: 'English' }, // 英语
        { key: 'zh-CN', name: '中文' } // 中文
      ]
    }
  },
  methods: {
    handle(value) {
      this.$i18n.locale = value
    }
  }
}
</script>
<style scoped lang="less">
</style>
