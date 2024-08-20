<script setup></script>

<template>
  <div id="app">
    <AppHeader />
    <router-view />
  </div>
</template>

<script>
import { onMounted } from 'vue'
import Header from '@/components/Header.vue'
import { useStationStore } from '@/stores/station'

export default {
  name: 'App',
  components: {
    AppHeader: Header
  },
  setup() {
    const stationStore = useStationStore()

    onMounted(() => {
      stationStore.fetchStations()
    })
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
