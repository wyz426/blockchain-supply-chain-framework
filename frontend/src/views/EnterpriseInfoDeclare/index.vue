<template>
  <div class="container">
    <h2>企业信息申报</h2>
    <br>
    <el-form ref="form" :model="form" label-width="auto">
      <el-form-item label="企业名称">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="经营范围">
        <el-input v-model="form.description" />
      </el-form-item>
      <el-form-item label="统一社会信用代码">
        <el-input v-model="form.credit_code" />
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" placeholder="xx省xx市xx县/区" />
      </el-form-item>
      <el-form-item label="邮编">
        <el-input v-model="form.post_code" />
      </el-form-item>
      <el-form-item label="企业邮箱">
        <el-input v-model="form.email" disabled />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped>
.container {
  text-align: center;
}

.el-input {
  margin-top: 5px;
  margin-bottom: 7px;
  width: 300px;
}

.el-button {
  margin-top: 20px;
  margin-left: 0px;
}

.el-form {
  margin-left: 30%;
  margin-right: 30%;
}
</style>

<script>
import Axios from 'axios'

export default {
  data() {
    return {
      form: {
        name: '',
        description: '', // 描述及经营范围
        credit_code: '', // 统一信用代码
        address: '',
        post_code: '', // 邮编
        email: sessionStorage.getItem('email'),
        status: '未审核',
        created_time: '',
        updated_time: ''
      }
    }
  },
  methods: {
    // 将时间戳转换成可读格式
    formatTimestamp(timestamp) {
      const date = new Date(timestamp)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      const seconds = String(date.getSeconds()).padStart(2, '0')
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
    },
    onSubmit() {
      const userId = sessionStorage.getItem('userId')
      console.log(userId)
      this.form.created_time = this.formatTimestamp(new Date().getTime())
      this.form.updated_time = this.formatTimestamp(new Date().getTime())
      const corp = {
        user_uuid: userId,
        name: this.form.name,
        description: this.form.description,
        credit_code: this.form.credit_code,
        post_code: this.form.post_code,
        email: this.form.email,
        status: this.form.status,
        address: this.form.address,
        createdTime: this.form.created_time,
        updatedTime: this.form.updated_time
      }
      console.log(corp)
      // 发送POST请求
      Axios.post(`http://localhost:8080/corporation/${userId}`, corp)
        .then(response => {
          console.log(response)
          this.$message.success('提交成功！')
          setTimeout(function() {
            window.location.reload()
          }, 1000) // 1000 毫秒 = 1 秒
        })
      console.log('submit over!')
      this.$router.push('/dashboard')
    }
  }
}
</script>
