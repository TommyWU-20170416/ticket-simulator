package com.wsi.ticketservices.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

//    @Before("execution(* com.wsi.ticketservices.controller.TicketController.ticket(..)) || " +
//            "execution(* com.wsi.ticketservices.controller.TicketController.ticketWithRedis(..))" +
//            "execution(* com.wsi.ticketservices.controller.TicketController.ticketWithRedisAndLock(..))")
//    public void logBefore(JoinPoint joinPoint) {
//        logger.info(LocalDateTime.now() + " 賣票: " + Thread.currentThread().getName());
//    }

    @After("execution(* com.wsi.ticketservices.controller.TicketController.ticket(..)) || " +
            "execution(* com.wsi.ticketservices.controller.TicketController.ticketWithRedis(..))" +
            "execution(* com.wsi.ticketservices.controller.TicketController.ticketWithRedisAndLock(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info(LocalDateTime.now() + " 賣票: " + Thread.currentThread().getName());
    }

}
