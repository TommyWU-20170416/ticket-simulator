<template>
  <div>
    <h2>會員資料管理</h2>
    <form @submit.prevent="updateProfile">
      <div>
        <label for="name">Name:</label>
        <input type="text" id="name" v-model="name" required />
      </div>
      <div>
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="email" required />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" />
      </div>
      <button type="submit">更新資料</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'ProfileView',
  data() {
    return {
      name: '',
      email: '',
      password: ''
    }
  },
  methods: {
    updateProfile() {
      // 處理更新資料邏輯
    }
  }
}
</script>

<style scoped>
/* 添加一些基本樣式 */
</style>
