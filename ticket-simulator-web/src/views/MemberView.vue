<template>
  <div>
    <h1>會員服務</h1>
    <router-link to="/member/signin">登入會員</router-link> |
    <router-link to="/member/register">註冊會員</router-link> |
    <router-link to="/member/history">會員購票歷史紀錄</router-link> |
    <router-link to="/member/profile">會員資料管理</router-link> |
    <router-link to="/member/management">後臺管理</router-link>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'MemberView'
}
</script>

<style scoped>
/* 添加一些基本樣式 */
</style>
