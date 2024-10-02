package com.wsi.walletservices.service;

import com.wsi.walletservices.exception.WalletNotFoundException;
import com.wsi.walletservices.model.TransactionType;
import com.wsi.walletservices.model.Wallet;
import com.wsi.walletservices.model.WalletLog;
import com.wsi.walletservices.repository.WalletRepository;
import com.wsi.walletservices.repository.WalleteLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalleteLogRepository walletLogRepository;

    /**
     * 因為 findByIdForUpdate 使用 row lock，在整個 transactional 沒有結束前，該 row 都是 lock 的
     */
    @Transactional
    public Wallet payPessimisticWrite(Integer walletId, TransactionType transactionType, Integer amount) {
        Wallet wallet = walletRepository.findByIdForUpdate(walletId);
        if (wallet.getWalletAmount().compareTo(amount) < 0) {
            throw new RuntimeException("餘額不足");
        }
        wallet.setWalletAmount(wallet.getWalletAmount() - amount);
        Wallet updatedWallet = walletRepository.save(wallet);
        writeWalletLog(updatedWallet, transactionType, amount);
        return updatedWallet;
    }

    /**
     * 樂觀鎖會在事務提交後，去檢查 version ，如果此時版本跟一開始讀取的版本不一樣，就會拋出 OptimisticLockingFailureException
     * 這時候就需要使用者自己去 retry
     * <p>
     * 這是 JPA show sql 內容，可以按到最後 where 有 version
     * update wallet set user_id=?, version=?, wallet_amount=?
     * where wallet_id=? and version=?
     */
    private static final int MAX_RETRY = 30;
    private static final int RETRY_DELAY_MS = 200;

    private static int maxRetry = 0; // request 併發的情況下 try 最多次的

    public Wallet payOptimisticWrite(Integer walletId, TransactionType transactionType, Integer amount) {
        int retryDelay = RETRY_DELAY_MS;
        for (int i = 0; i < MAX_RETRY; i++) {
            if (i > maxRetry) {
                maxRetry = i;
                System.out.println("maxRetry: " + maxRetry);
            }
            try {
                Wallet updatedWallet = attempPayOptimisticWrite(walletId, amount);
                writeWalletLog(updatedWallet, transactionType, amount);
                return updatedWallet;
            } catch (OptimisticLockingFailureException e) {
                System.out.println(Thread.currentThread().getName() + "樂觀鎖被取走了，重試");
                if (i == MAX_RETRY - 1) {
                    throw e;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(retryDelay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", ie);
                }
            }
        }
        throw new RuntimeException("retry 失敗");
    }

    /**
     * 為什麼要拆出另一個 method 呢？
     * <p>
     * 我們使用 樂觀鎖，預期就會有發生 OptimisticLockingFailureException 的可能，因此我們也需要進行 retry 的機制
     * 但是如果我們在 service 裡面進行 retry(已經 catch 到 OptimisticLockingFailureException)，此時 transactional 會被標記 rollback
     * 雖然沒有立即跳出去，因為被我們 catch 住且又 retry，但就算 retry 也是無法正常送出去
     * <p>
     * 因此把服務拆開，執行 walletRepository.save(wallet) 有 OptimisticLockingFailureException 時，由 payOptimisticWrite 進行 retry
     * 這樣也保證下一次近來就會是正常的 transactional
     */
    @Transactional
    public Wallet attempPayOptimisticWrite(Integer walletId, Integer amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException("錢包不存在"));
        if (wallet.getWalletAmount().compareTo(amount) < 0) {
            throw new RuntimeException("餘額不足");
        }
        wallet.setWalletAmount(wallet.getWalletAmount() - amount);
        Wallet updatedWallet = walletRepository.save(wallet); // 立即刷新，觸發異常
        return updatedWallet;
    }

    // 雖然這邊沒有加上 @Transactional，但是因為在 @Transactional 的方法被呼叫
    // 所以這邊也會被包在同一個 transaction 裡面
    // 除非 writeWalletLog 有自己做 try catch，否則有錯誤也是會讓整個 transaction rollback
    public void writeWalletLog(Wallet wallet, TransactionType transactionType, Integer amount) {
        WalletLog walletLog = new WalletLog();
        walletLog.setWalletId(wallet.getWalletId());
        walletLog.setWalletTransaction(transactionType.getValue());
        walletLog.setAmountTransaction(amount);
        walletLog.setCreatedAt(LocalDateTime.now());
        walletLogRepository.save(walletLog);
    }

    public Wallet insertWallet(@RequestBody Wallet wallet) {
        Wallet savedWallet = walletRepository.save(wallet);
        return savedWallet;
    }


    @Transactional
    public Wallet infoPessimisticWrite(Integer walletId) {
        return walletRepository.findByIdForUpdate(walletId);
    }

    public Wallet infoOptimisticWrite(Integer walletId) {
        int retryDelay = RETRY_DELAY_MS;
        for (int i = 0; i < MAX_RETRY; i++) {
            if (i > maxRetry) {
                maxRetry = i;
                System.out.println("maxRetry: " + maxRetry);
            }
            try {
                return attemptInfoOptimisticWrite(walletId);
            } catch (OptimisticLockingFailureException e) {
                System.out.println(Thread.currentThread().getName() + "樂觀鎖被取走了，重試");
                if (i == MAX_RETRY - 1) {
                    throw e;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(retryDelay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", ie);
                }
            }
        }
        throw new RuntimeException("retry 失敗");
    }


    @Transactional
    public Wallet attemptInfoOptimisticWrite(Integer walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException("錢包不存在"));
        return wallet;
    }
}
