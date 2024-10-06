import axios from 'axios'

const travelServices = axios.create({
  baseURL: '/api/travelServices'
  // 你可以在這裡添加更多的配置，例如 headers
})

export const ticketServices = axios.create({
  baseURL: '/api/ticketServices'
  // 你可以在這裡添加更多的配置，例如 headers
})

export default travelServices
