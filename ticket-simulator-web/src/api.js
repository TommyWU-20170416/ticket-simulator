import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8541'
  // 你可以在這裡添加更多的配置，例如 headers
})

export const ticketServices = axios.create({
  baseURL: 'http://localhost:8542'
  // 你可以在這裡添加更多的配置，例如 headers
})

export default api
