<template>
  <div>
    <h1>旅客服務</h1>
    <router-link to="/customer/schedule">查詢班次</router-link> |
    <router-link to="/customer/ticketstype">票卷種類</router-link> |
    <router-link to="/customer/ticketprice">票價試算</router-link>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'CustomerView'
}
</script>

<style scoped>
/* 添加一些基本樣式 */
</style>
