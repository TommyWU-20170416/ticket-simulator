<template>
  <header>
    <nav>
      <router-link to="/home">首頁</router-link>
      <router-link to="/customer">旅客服務</router-link>
      <router-link to="/member">會員服務</router-link>
      <router-link to="/ticket">線上訂票</router-link>
      <router-link to="/travel">鐵道旅遊</router-link>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'Header'
}
</script>

<style scoped>
header {
  background-color: #333;
  padding: 1rem;
  color: white;
}

nav {
  display: flex;
  justify-content: space-around;
}

a {
  color: white;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style>
