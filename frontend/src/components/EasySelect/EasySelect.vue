<template>
  <el-select v-model="selectedValue" placeholder="请选择">
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
</template>

<script>
export default {
  props: {
    initialOptions: {
      type: Array,
      default: () => [
        { value: '选项1', label: '黄金糕' },
        { value: '选项2', label: '双皮奶' },
        { value: '选项3', label: '蚵仔煎' }
      ]
    }
  },
  data() {
    return {
      options: this.initialOptions,
      selectedValue: ''
    }
  }
}
</script>

<style scoped>

</style>

