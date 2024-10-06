import axios from 'axios'

const travelServices = axios.create({
  baseURL: 'http://localhost:8541/api/travelServices'
  // 你可以在這裡添加更多的配置，例如 headers
})

export const ticketServices = axios.create({
  baseURL: 'http://localhost:8551/api/ticketServices'
  // 你可以在這裡添加更多的配置，例如 headers
})

export default travelServices
