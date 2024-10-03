package com.wsi.ticketservices.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsi.ticketservices.dto.BuyTicketResponse;
import com.wsi.ticketservices.dto.TicketPurchaseRequest;
import com.wsi.ticketservices.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @Autowired
    private TicketService ticketService;

//    @KafkaListener(topics = "${kafka.topic.ticket_purchase.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) throws JsonProcessingException {
        TicketPurchaseRequest ticketPurchaseRequest = new ObjectMapper().readValue(message, TicketPurchaseRequest.class);
        BuyTicketResponse buyTicketResponse = ticketService.buyTicket(ticketPurchaseRequest);
    }
}
