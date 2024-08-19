<script setup></script>

<template>
  <div id="app">
    <header>
      <h1>搶票網站</h1>
    </header>
    <main>
      <TicketList />
    </main>
  </div>
</template>

<script>
import TicketList from './components/TicketList.vue'

export default {
  name: 'App',
  components: {
    TicketList
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
