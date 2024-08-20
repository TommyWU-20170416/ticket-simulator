<template>
  <div>
    <h2>會員購票歷史紀錄</h2>
    <ul>
      <li v-for="record in ticketHistory" :key="record.id">
        {{ record.date }} - {{ record.details }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'HistoryView',
  data() {
    return {
      ticketHistory: [
        // 假數據，應該從 API 獲取
        { id: 1, date: '2023-01-01', details: '台北到高雄' },
        { id: 2, date: '2023-02-01', details: '台中到台南' }
      ]
    }
  }
}
</script>

<style scoped>
/* 添加一些基本樣式 */
</style>
