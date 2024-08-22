<template>
  <div>
    <div v-show="loading" class="loading-overlay">
      <p>Loading...</p>
    </div>
    <h1>線上訂票</h1>
    <!-- <form @submit.prevent="searchTickets">
      <div>
        <label for="username">使用者名稱:</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div>
        <label for="departure">出發站:</label>
        <input type="text" id="departure" v-model="departure" required />
      </div>
      <div>
        <label for="arrival">抵達站:</label>
        <input type="text" id="arrival" v-model="arrival" required />
      </div>
      <div>
        <label for="ticketCount">票數:</label>
        <input type="number" id="ticketCount" v-model="ticketCount" required min="1" />
      </div>
      <button type="submit">查詢</button>
    </form> -->
    <div>
      <h2>查詢結果</h2>
      <table>
        <thead>
          <tr>
            <th>車種車次</th>
            <th>出發地點/時間</th>
            <th>抵達地點/時間</th>
            <th>旅程時間</th>
            <th>全票(單張)價錢</th>
            <th>買票</th>
            <th>剩下幾張</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(schedule, index) in schedules" :key="schedule.id">
            <td>{{ schedule.scheduleName }}</td>
            <td>{{ schedule.departureStation + ' / ' + schedule.scheduleDepartureTime }}</td>
            <td>{{ schedule.arrivalStation + ' / ' + schedule.scheduleArrivalTime }}</td>
            <td>{{ schedule.duration }}</td>
            <td>{{ schedule.price }}</td>
            <td>
              <button @click="buyTicket(schedule, index)">買票</button>
            </td>
            <td>{{ schedule.scheduleTicketLess }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { ticketServices } from '@/api'

export default {
  name: 'TicketView',
  setup() {
    const username = ref('')
    const departure = ref('')
    const arrival = ref('')
    const schedules = ref([])
    const schedule = ref({})
    const loading = ref(false)

    onMounted(() => {
      searchTickets()
    })

    // schedules.value = [
    // {
    //     "scheduleId": 841,
    //     "scheduleName": "0881",
    //     "scheduleTicketTotal": 500,
    //     "scheduleTicketLess": 500,
    //     "scheduleDepartureTime": "2024-08-22T14:00:00",
    //     "scheduleArrivalTime": "2024-08-22T15:50:00",
    //     "departureStation": {
    //         "stationId": 1,
    //         "stationName": "南港",
    //         "stationIndex": "1"
    //     },
    //     "arrivalStation": {
    //         "stationId": 14,
    //         "stationName": "台中",
    //         "stationIndex": "14"
    //     }
    // }]
    const searchTickets = async () => {
      try {
        schedules.value = []
        schedule.value = {}
        const response = await ticketServices.get('/schedule')
        formatSchedule(response.data)
        console.log('查詢成功', response.data)
        return response.data
      } catch (error) {
        console.error('查詢失敗', error)
      }
    }

    const formatSchedule = (response) => {
      response.forEach((res) => {
        console.log(res)
        schedule.value.id = res.scheduleId
        schedule.value.scheduleName = res.scheduleName
        schedule.value.scheduleDepartureTime = new Date(res.scheduleDepartureTime).toLocaleString()
        schedule.value.scheduleArrivalTime = new Date(res.scheduleArrivalTime).toLocaleString()
        schedule.value.duration =
          Math.floor(
            (new Date(res.scheduleArrivalTime) - new Date(res.scheduleDepartureTime)) / 60000
          ) + '分鐘'
        schedule.value.price = 500
        schedule.value.departureStation = res.departureStation.stationName
        schedule.value.arrivalStation = res.arrivalStation.stationName
        schedule.value.scheduleTicketLess = res.scheduleTicketLess
        schedules.value.push(schedule.value)
        schedule.value = {}
      })
    }

    const buyTicket = async (schedule, index) => {
      loading.value = true
      try {
        const response = await ticketServices.post('/ticketswithredisandlock', {
          scheduleId: schedule.id,
          username: username.value
        })
        console.log('買到票了', response.data)
        schedules.value[index].scheduleTicketLess = response.data.scheduleTicketLess
      } catch (error) {
        console.error('票不夠', error)
      } finally {
        loading.value = false
      }
    }

    return {
      username,
      departure,
      arrival,
      schedules,
      buyTicket,
      loading
    }
  }
}
</script>

<style scoped>
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 2em;
}
/* 添加一些基本樣式 */
form div {
  margin-bottom: 10px;
}
table {
  width: 100%;
  border-collapse: collapse;
}
th,
td {
  border: 1px solid #ddd;
  padding: 8px;
}
th {
  background-color: #f2f2f2;
}
</style>
