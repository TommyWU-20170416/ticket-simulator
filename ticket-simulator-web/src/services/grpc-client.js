import * as grpcWeb from 'grpc-web'
import { TicketServiceClient } from '@/generated/ticket_pb_service' // 根據您的 proto 文件生成的服務
import { BuyTicketRequest, BuyTicketResponse } from '@/generated/ticket_pb' // 根據您的 proto 文件生成的請求和響應

const ticketService = new TicketServiceClient('http://localhost:8551', null, null)

export function buyTicket(ticketId, username, departure, arrival, ticketCount) {
  const request = new BuyTicketRequest()
  request.setTicketId(ticketId)
  request.setUsername(username)
  request.setDeparture(departure)
  request.setArrival(arrival)
  request.setTicketCount(ticketCount)

  return new Promise((resolve, reject) => {
    ticketService.buyTicket(request, {}, (err, response) => {
      if (err) {
        reject(err)
      } else {
        resolve(response)
      }
    })
  })
}
