<template>
  <div>
    <h1>鐵道旅遊</h1>
    <label for="station-select">選擇車站:</label>
    <select id="station-select" v-model="selectedStation">
      <option v-for="station in stations" :key="station.stationId" :value="station.stationId">
        {{ station.stationName }}
      </option>
    </select>

    <div>
      <button @click="handleRestaurantsClick">餐廳</button>
      <button @click="handleAttractionsClick">景點</button>
    </div>

    <div v-if="currentTab === 'restaurants'">
      <h2>餐廳</h2>
      <div class="grid">
        <div v-for="restaurant in paginatedRestaurants" :key="restaurant.id" class="grid-item">
          <img
            :src="getImageUrl(restaurant.restaurantImageBlob)"
            :alt="restaurant.restaurantName"
          />
          <p>{{ restaurant.restaurantName }}</p>
        </div>
      </div>
    </div>
    <div v-if="currentTab === 'attractions'">
      <h2>景點</h2>
      <div class="grid">
        <div v-for="attraction in paginatedAttractions" :key="attraction.id" class="grid-item">
          <img
            :src="getImageUrl(attraction.attractionImageBlob)"
            :alt="attraction.attractionName"
          />
          <p>{{ attraction.attractionName }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useStationStore } from '@/stores/station'
import api from '@/api' // 引入共用的 Axios 實例

export default {
  name: 'TravelView',
  setup() {
    const stationStore = useStationStore()
    const stations = stationStore.stations
    const selectedStation = ref(null)
    const currentTab = ref('restaurants')
    const paginatedRestaurants = ref([])
    const paginatedAttractions = ref([])

    // const fetchStations = async () => {
    //   try {
    //     const response = await api.get('/stations')
    //     stations.value = response.data
    //   } catch (error) {
    //     console.error('Error fetching stations:', error)
    //   }
    // }

    const fetchRestaurants = async (stationId) => {
      try {
        const response = await api.get('/restaurants/' + stationId)
        paginatedRestaurants.value = response.data
      } catch (error) {
        console.error('Error fetching restaurants:', error)
      }
    }

    const handleRestaurantsClick = () => {
      currentTab.value = 'restaurants'
      if (selectedStation.value) {
        fetchRestaurants(selectedStation.value)
      }
    }

    const fetchAttractions = async (stationId) => {
      try {
        const response = await api.get('/attractions/' + stationId)
        paginatedAttractions.value = response.data
      } catch (error) {
        console.error('Error fetching restaurants:', error)
      }
    }

    const handleAttractionsClick = () => {
      currentTab.value = 'attractions'
      if (selectedStation.value) {
        fetchAttractions(selectedStation.value)
      }
    }

    const getImageUrl = (base64Image) => {
      return `data:image/png;base64,${base64Image}`
    }

    return {
      stations,
      selectedStation,
      currentTab,
      paginatedRestaurants,
      fetchRestaurants,
      fetchAttractions,
      handleRestaurantsClick,
      handleAttractionsClick,
      getImageUrl
    }
  }
}
</script>

<style scoped>
/* 你的樣式 */
</style>
