<template>
    <div>
      <h1>鐵道旅遊</h1>
      <label for="station-select">選擇車站:</label>
      <select id="station-select" v-model="selectedStation" @change="fetchData">
        <option v-for="station in stations" :key="station.id" :value="station.id">{{ station.name }}</option>
      </select>
  
      <div>
        <button @click="currentTab = 'restaurants'">餐廳</button>
        <button @click="currentTab = 'attractions'">景點</button>
      </div>
  
      <div v-if="currentTab === 'restaurants'">
        <h2>餐廳</h2>
        <div class="grid">
          <div v-for="restaurant in paginatedRestaurants" :key="restaurant.id" class="grid-item">
            <img :src="restaurant.image" :alt="restaurant.name" />
            <p>{{ restaurant.name }}</p>
          </div>
        </div>
        <pagination :total="restaurants.length" :current="currentPage" @change="handlePageChange" />
      </div>
  
      <div v-if="currentTab === 'attractions'">
        <h2>景點</h2>
        <div class="grid">
          <div v-for="attraction in paginatedAttractions" :key="attraction.id" class="grid-item">
            <img :src="attraction.image" :alt="attraction.name" />
            <p>{{ attraction.name }}</p>
          </div>
        </div>
        <pagination :total="attractions.length" :current="currentPage" @change="handlePageChange" />
      </div>
    </div>
  </template>
  
  <script>
  import Pagination from '@/components/Pagination.vue'
  
  export default {
    name: 'TravelView',
    components: {
      Pagination
    },
    data() {
      return {
        stations: [], // 車站列表
        selectedStation: null,
        currentTab: 'restaurants',
        restaurants: [], // 餐廳列表
        attractions: [], // 景點列表
        currentPage: 1,
        itemsPerPage: 9
      }
    },
    computed: {
      paginatedRestaurants() {
        const start = (this.currentPage - 1) * this.itemsPerPage
        const end = start + this.itemsPerPage
        return this.restaurants.slice(start, end)
      },
      paginatedAttractions() {
        const start = (this.currentPage - 1) * this.itemsPerPage
        const end = start + this.itemsPerPage
        return this.attractions.slice(start, end)
      }
    },
    methods: {
      fetchData() {
        // 根據 selectedStation 獲取餐廳和景點數據
        // 這裡應該有 API 調用來獲取數據
        // 假設我們有一個 API 可以調用
        // this.restaurants = fetchRestaurants(this.selectedStation)
        // this.attractions = fetchAttractions(this.selectedStation)
      },
      handlePageChange(page) {
        this.currentPage = page
      }
    },
    mounted() {
      // 獲取車站列表
      // this.stations = fetchStations()
    }
  }
  </script>
  
  <style scoped>
  .grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 1rem;
  }
  .grid-item {
    text-align: center;
  }
  </style>