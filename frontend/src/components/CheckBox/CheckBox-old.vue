<template>
  <div>
    <div>
      <el-checkbox v-model="checked1" label="资料管理" border disabled />
      <el-checkbox v-model="checked2" label="采购管理" border disabled />
    </div>
    <div style="margin-top: 20px">
      <el-checkbox v-model="checked3" label="仓库管理" border disabled />
      <el-checkbox v-model="checked4" label="销售管理" border disabled />
      <el-checkbox v-model="checked5" label="物流管理" border disabled />
    </div>
    <div style="margin-top: 20px">
      <el-checkbox v-model="checked6" label="生产管理" border />
      <el-checkbox v-model="checked7" label="质量管理" border />
    </div>
    <div style="margin-top: 20px">
      <el-checkbox v-model="checked8" label="产品溯源" border />
      <el-checkbox v-model="checked9" label="监管审计" border />
      <el-checkbox v-model="checked10" label="产品召回" border />
    </div>
    <div style="margin-top: 30px">
      <el-button type="primary" @click="onNext">下一步</el-button>
      <el-button @click="onCancel">取消</el-button>
    </div>
  </div>
</template>

<script>
export default {
  props: ['dialogVisible'],
  data() {
    return {
      checked1: true,
      checked2: true,
      checked3: true,
      checked4: true,
      checked5: true,
      checked6: false,
      checked7: false,
      checked8: false,
      checked9: false,
      checked10: false
    }
  },
  methods: {
    onNext() {
      console.log('submit!')
      const selectedOptions = {
        checked1: this.checked1,
        checked2: this.checked2,
        checked3: this.checked3,
        checked4: this.checked4,
        checked5: this.checked5,
        checked6: this.checked6,
        checked7: this.checked7,
        checked8: this.checked8,
        checked9: this.checked9,
        checked10: this.checked10
      }
      this.$emit('nextStep', selectedOptions)
    },
    onCancel() {
      console.log('cancel.')
      this.$emit('cancel')
    }
  }
}
</script>

<style scoped>

</style>
