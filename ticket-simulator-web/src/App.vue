<script setup></script>

<template>
  <div id="app">
    <AppHeader />
    <router-view />
  </div>
</template>

<script>
import Header from '@/components/Header.vue'

export default {
  name: 'App',
  components: {
      AppHeader: Header
  }
}
</script>

<style>
/* 添加一些基本樣式 */
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
header {
  background-color: #42b983;
  padding: 20px;
  color: white;
}
</style>
