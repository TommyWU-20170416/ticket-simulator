<template>
  <div>
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
            <th>出發時間</th>
            <th>抵達時間</th>
            <th>旅程時間</th>
            <th>全票(單張)價錢</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ticket in tickets" :key="ticket.id">
            <td>{{ ticket.trainType }}</td>
            <td>{{ ticket.departureTime }}</td>
            <td>{{ ticket.arrivalTime }}</td>
            <td>{{ ticket.duration }}</td>
            <td>{{ ticket.price }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: 'TicketView',
  setup() {
    const username = ref('')
    const departure = ref('')
    const arrival = ref('')
    const ticketCount = ref(1)
    const tickets = ref([])
    tickets.value = [
      {
        id: 1,
        trainType: '高鐵123',
        departureTime: '08:00',
        arrivalTime: '10:00',
        duration: '2小時',
        price: '1000元'
      },
      {
        id: 2,
        trainType: '高鐵456',
        departureTime: '09:00',
        arrivalTime: '11:00',
        duration: '2小時',
        price: '1000元'
      }
    ]

    return {
      username,
      departure,
      arrival,
      ticketCount,
      tickets
    }
  }
}
</script>

<style scoped>
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
