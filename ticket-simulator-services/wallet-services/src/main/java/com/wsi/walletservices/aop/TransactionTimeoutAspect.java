package com.wsi.walletservices.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionTimedOutException;


@Aspect
@Component
public class TransactionTimeoutAspect {
    private static final Logger logger = LoggerFactory.getLogger(TransactionTimeoutAspect.class);

    // 看是不是有 timeout 的連線
    @AfterThrowing(pointcut = "execution(* com.wsi.walletservices.service.WalletService.*(..))", throwing = "ex")
    public void logTransactionTimeout(TransactionTimedOutException ex) {
        logger.error("Transaction timeout: {}", ex.getMessage());
    }
}
