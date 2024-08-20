import { defineStore } from 'pinia';
import api from '@/api'; // 引入共用的 Axios 實例

export const useStationStore = defineStore('station', {
  state: () => ({
    stations: [],
  }),
  actions: {
    async fetchStations() {
      try {
        const response = await api.get('/stations');
        this.stations = response.data;
      } catch (error) {
        console.error('Error fetching stations:', error);
      }
    },
  },
});