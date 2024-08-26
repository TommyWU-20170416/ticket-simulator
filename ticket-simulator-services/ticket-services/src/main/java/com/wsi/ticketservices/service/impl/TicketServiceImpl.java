package com.wsi.ticketservices.service.impl;

import com.wsi.ticketservices.dto.BuyTicketResponse;
import com.wsi.ticketservices.dto.TicketPurchaseRequest;
import com.wsi.ticketservices.model.Schedule;
import com.wsi.ticketservices.repository.ScheduleRepository;
import com.wsi.ticketservices.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private RedisTemplate<String, String> redisTemplateString;
    @Autowired
    private RedisTemplate<String, Schedule> redisTemplateSchedule;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private static final String SCHEDULE_CACHE_PREFIX = "schedule:";
    private static final String LOCK_KEY_PREFIX = "lock:";

    @Override
    public Schedule updateSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule getScheduleById(Integer scheduleId) {
        Schedule schedule = null;
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        if (optionalSchedule.isPresent()) {
            schedule = optionalSchedule.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }

        return schedule;
    }

    /**
     * 使用 Redis 快取的步驟
     */
    @Override
    public Schedule getScheduleByIdWithRedis(Integer scheduleId) {
        String redisKey = SCHEDULE_CACHE_PREFIX + scheduleId;
        // 先從 Redis 快取中查找
        Schedule schedule = redisTemplateSchedule.opsForValue().get(redisKey);

        if (schedule == null) {
            // Redis 中無資料，從資料庫中查詢
            Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
            if (optionalSchedule.isPresent()) {
                schedule = optionalSchedule.get();
                // 將結果寫入 Redis 快取
                redisTemplateSchedule.opsForValue().set(redisKey, schedule, 10, TimeUnit.MINUTES);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
            }
        }

        return schedule;
    }

    /**
     * 使用 Redis 分佈式鎖的步驟
     * 獲取鎖：在購票操作前，獲取一個唯一的 Redis 鎖，這個鎖應該與班次 ID 綁定。只有獲取到鎖的請求才能進行扣票操作。
     * 執行購票操作：當獲取到鎖後，檢查票數並進行扣減，然後保存到資料庫和更新 Redis 快取。
     * 釋放鎖：購票操作完成後，立即釋放鎖，允許其他請求進行操作。
     * <p>
     * 購票操作
     */
    @Override
    public synchronized BuyTicketResponse buyTicket(TicketPurchaseRequest ticketPurchaseRequest) {
        String scheduleId = ticketPurchaseRequest.getScheduleId().toString();
        String lockKey = LOCK_KEY_PREFIX + scheduleId;
        String lockValue = UUID.randomUUID().toString();

        try {
            // 嘗試獲取鎖，設置過期時間來避免死鎖
            Boolean lock = redisTemplateString.opsForValue().setIfAbsent(lockKey, lockValue, 10, TimeUnit.SECONDS);
            if (Boolean.TRUE.equals(lock)) {
                // 拿到鎖，進行購票操作
                Schedule schedule = getScheduleByIdWithRedis(ticketPurchaseRequest.getScheduleId());
                schedule.setScheduleTicketLess(schedule.getScheduleTicketLess() - 1);
                scheduleRepository.save(schedule);
                redisTemplateSchedule.opsForValue().set(SCHEDULE_CACHE_PREFIX + scheduleId, schedule, 10, TimeUnit.MINUTES);

                return new BuyTicketResponse(schedule.getScheduleTicketLess());
            }else{
                System.out.println("Another process is buying tickets, please try again.");
                throw new ResponseStatusException(HttpStatus.LOCKED, "Another process is buying tickets, please try again.");
            }
        } finally {
            // 釋放鎖
            if (lockValue.equals(redisTemplateString.opsForValue().get(lockKey))) {
                redisTemplateString.delete(lockKey);
            }
        }
    }
}
