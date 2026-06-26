<template>
  <div class="register-container">
    <el-card
      shadow="always"
      :body-style="{ padding: '20px' }"
      style="width: 400px; margin: 0 auto;"
    >
      <h2>企业账号注册</h2>
      <el-input v-model="email" placeholder="请输入账号(邮箱)" />
      <br>
      <el-input v-model="password" placeholder="请输入密码" show-password />
      <br>
      <el-input v-model="password_2" placeholder="请再次输入密码" show-password />
      <br>
      <el-row>
        <h4>选择账号权限</h4>
        <el-radio v-model="role" label="ADMIN">管理员</el-radio>
        <el-radio v-model="role" label="ENTERPRISE">普通企业</el-radio>
        <el-radio v-model="role" label="SUPERVISOR">监管组织</el-radio>
      </el-row>
      <br>
      <el-button
        type="info"
        icon="el-icon-back"
        style="margin-right: 20px"
        @click="goToLogin"
      >返回</el-button>
      <el-button type="primary" icon="el-icon-check" @click="RegisterUser">注册</el-button>
    </el-card>
  </div>
</template>

<style scoped>
.el-input {
  margin-top: 10px;
  margin-bottom: 10px;
  width: 300px;
}

.el-button {
  margin-top: 20px;
  margin-left: 0px;
}

.register-container {
  text-align: center;
  margin-top: 100px;
}
</style>

<script>
import { register } from '@/api/user'

export default {
  data() {
    return {
      email: '',
      password: '',
      role: 'ADMIN',
      password_2: ''
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
    goToLogin() {
      this.$router.push('/login')
    },
    RegisterUser() {
      console.log(this.email, this.password, this.password_2)
      if (this.email === '' || this.password === '' || this.password_2 === '') {
        this.$message.error('输入不能为空')
        return
      }
      if (this.password.length < 6) {
        this.$message.error('密码长度不能小于6位')
        return
      }
      if (this.password !== this.password_2) {
        this.$message.error('两次输入的密码不一致')
        return
      }

      const user = {
        email: this.email,
        password: this.password,
        role: this.role,
        created_time: this.formatTimestamp(new Date().getTime()),
        updated_time: this.formatTimestamp(new Date().getTime())
      }

      // 发送请求给后端
      register(user)
      this.$message.success('注册成功')
      this.$router.push('/login')
    }
  }
}
</script>
