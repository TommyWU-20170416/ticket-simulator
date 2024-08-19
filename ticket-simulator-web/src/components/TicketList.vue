<template>
    <div>
      <h2>可用票券</h2>
      <ul>
        <li v-for="ticket in tickets" :key="ticket.id">
          {{ ticket.name }} - {{ ticket.price }} 元
          <button @click="bookTicket(ticket.id)">搶票</button>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    name: 'TicketList',
    data() {
      return {
        tickets: [
          { id: 1, name: '演唱會', price: 1000 },
          { id: 2, name: '電影', price: 300 },
          { id: 3, name: '戲劇', price: 500 }
        ]
      };
    },
    methods: {
      bookTicket(ticketId) {
        alert(`搶票成功！票券ID: ${ticketId}`);
      }
    }
  };
  </script>
  
  <style scoped>
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    margin: 10px 0;
  }
  button {
    margin-left: 10px;
  }
  </style>