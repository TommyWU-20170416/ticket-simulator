<template>
  <div>
    <h2>登入會員</h2>
    <form @submit.prevent="signIn">
      <div>
        <label for="email">Email:</label>
        <input id="email" v-model="email" />
        <!-- <input type="email" id="email" v-model="email" required /> 這個會擋檢核先註解 -->
      </div>
      <div>
        <label for="password">Password:</label>
        <input id="password" v-model="password" />
        <!-- <input type="password" id="password" v-model="password" required /> 這個會擋檢核先註解  -->
      </div>
      <button type="submit">登入</button>
    </form>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router' // Import the useRouter function from vue-router

export default {
  name: 'SignInView',
  setup() {
    const email = ref('')
    const password = ref('')
    const router = useRouter() // Create a router object using the useRouter function

    const signIn = () => {
      // 處理登入邏輯
      // 成功後導航到會員頁面
      router.push('/member/history') // Use the router object to navigate to the '/member/history' route
    }

    return {
      email,
      password,
      signIn
    }
  }
}
</script>

<style scoped>
/* 添加一些基本樣式 */
</style>
