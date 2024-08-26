package com.wsi.ticketservices.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsi.ticketservices.dto.BuyTicketResponse;
import com.wsi.ticketservices.dto.TicketPurchaseRequest;
import com.wsi.ticketservices.model.Schedule;
import com.wsi.ticketservices.producer.KafkaProducerService;
import com.wsi.ticketservices.repository.ScheduleRepository;
import com.wsi.ticketservices.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TicketController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @GetMapping("/schedule")
    public ResponseEntity<List<Schedule>> getSchedules() {
        List<Schedule> schedule = scheduleRepository.findAll();
        return ResponseEntity.ok(schedule);
    }

    /**
     * 查詢餘票：
     *
     * 1. 用戶發出查詢請求。
     * 系統先查詢 Redis 快取，如果存在，直接返回結果。
     * 如果 Redis 中無數據，則查詢資料庫，並將結果寫入 Redis。
     * 購票操作：
     *
     * 2. 獲取 Redis 分佈式鎖，鎖定該班次的票數變更操作。
     * 檢查 Redis 中的剩餘票數，確保足夠購買。
     * 如果足夠，減少 Redis 中的票數並同步到資料庫。
     * 釋放 Redis 鎖。
     */
    // 原本可以正常使用的，我另外開一個不影響
//    @PostMapping("/tickets")
//    public ResponseEntity<BuyTicketResponse> BuyTicket(@RequestBody TicketPurchaseRequest ticketPurchaseRequest) {
//        Schedule schedule = null;
//        Optional<Schedule> optionalSchedule = scheduleRepository.findById(ticketPurchaseRequest.getScheduleId());
//
//        // check if schedule exists
//        if (optionalSchedule.isPresent()) {
//            schedule = optionalSchedule.get();
//        }
//
//        // 票賣完回覆 409 或是票不構 為測試方便不先扣掉票數
////        if (schedule.getScheduleTicketLess().equals(0) || schedule.getScheduleTicketLess() < ticketPurchaseRequest.getTicketCount()) {
////            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tickets are sold out or not enough tickets.");
////        }
//        System.out.println(LocalDateTime.now() + " 賣票: " + Thread.currentThread().getName());
//        schedule.setScheduleTicketLess(schedule.getScheduleTicketLess() - 1);
//        scheduleRepository.save(schedule);
//
//        return ResponseEntity.ok(new BuyTicketResponse(schedule.getScheduleTicketLess()));
//    }

    @PostMapping("/tickets")
    public ResponseEntity<BuyTicketResponse> ticket(@RequestBody TicketPurchaseRequest ticketPurchaseRequest) {

        Schedule schedule = ticketService.getScheduleById(ticketPurchaseRequest.getScheduleId());
        schedule.setScheduleTicketLess(schedule.getScheduleTicketLess() - 1);
        Schedule newSchedule = ticketService.updateSchedule(schedule);

        return ResponseEntity.ok(new BuyTicketResponse(newSchedule.getScheduleTicketLess()));
    }

    @PostMapping("/ticketswithredis")
    public ResponseEntity<BuyTicketResponse> ticketWithRedis(@RequestBody TicketPurchaseRequest ticketPurchaseRequest) {

        Schedule schedule = ticketService.getScheduleByIdWithRedis(ticketPurchaseRequest.getScheduleId());
        schedule.setScheduleTicketLess(schedule.getScheduleTicketLess() - 1);
        Schedule newSchedule = ticketService.updateSchedule(schedule);

        return ResponseEntity.ok(new BuyTicketResponse(newSchedule.getScheduleTicketLess()));
    }

    @PostMapping("/ticketswithredisandlock")
    public ResponseEntity<BuyTicketResponse> ticketWithRedisAndLock(@RequestBody TicketPurchaseRequest ticketPurchaseRequest) {

        BuyTicketResponse buyTicketResponse = ticketService.buyTicket(ticketPurchaseRequest);

        return ResponseEntity.ok(buyTicketResponse);
    }

    /**
     * 使用 kafka 當作緩衝層
     * 將 ticket 資訊寫入 kafka，並由另一個服務監聽 kafka，進行購票操作。
     */
    @Value("${kafka.topic.ticket_purchase.name}")
    private String ticketPurchaseTopic;

    @PostMapping("/ticketswithkafkaandredisandlock")
    public ResponseEntity<BuyTicketResponse> ticketWithKafkaAndRedisAndLock(@RequestBody TicketPurchaseRequest ticketPurchaseRequest) throws JsonProcessingException {
        // Convert request to json and store in kafka
        String message = new ObjectMapper().writeValueAsString(ticketPurchaseRequest);
        kafkaProducerService.sendMessage(ticketPurchaseTopic, message);

        return ResponseEntity.accepted().build(); // 之後要改成等 kafka 做完回傳給前端
    }
}
