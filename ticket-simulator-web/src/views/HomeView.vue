<template>
  <div class="container">
    <h1>火車搶票系統</h1>
    <div class="buttons">
      <button @click="navigateTo('/customer')">旅客服務</button>
      <button @click="navigateTo('/member')">會員服務</button>
      <button @click="navigateTo('/ticket')">線上訂票</button>
      <button @click="navigateTo('/travel')">鐵道旅遊</button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const navigateTo = (path) => {
  router.push(path)
}
</script>

<style scoped>
body {
  font-family: Arial, sans-serif;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
  background-color: #f0f0f0;
}

.container {
  margin: 0 auto;
  max-width: 600px;
  text-align: center;
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
  margin-bottom: 20px;
}

.buttons {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

button {
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #007bff;
  color: #fff;
}
</style>
