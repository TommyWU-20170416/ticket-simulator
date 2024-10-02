package com.wsi.walletservices.controller;

import com.wsi.walletservices.dto.request.PayRequestDto;
import com.wsi.walletservices.model.TransactionType;
import com.wsi.walletservices.model.Wallet;
import com.wsi.walletservices.repository.WalletRepository;
import com.wsi.walletservices.schedule.InstallmentService;
import com.wsi.walletservices.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private InstallmentService installmentService;
    @PostMapping("/wallets")
    public Wallet insertWallet(@RequestBody Wallet wallet) {
        Wallet savedWallet = walletService.insertWallet(wallet);
        return savedWallet;
    }

    /**
     * 支付: 要做到餘額查詢，以及扣款，透過事務以及 row lock 方式避免超賣
     * <p>
     * 使用悲觀鎖的方式去避免超賣
     */
    @PostMapping("/wallets/payPessimisticWrite")
    public Wallet payPessimisticWrite(@RequestBody PayRequestDto payRequestDto) {
        Wallet savedWallet = walletService.payPessimisticWrite(payRequestDto.getWalletId(), TransactionType.DEPOSIT, payRequestDto.getAmount());

        return savedWallet;
    }

    /**
     * 支付: 要做到餘額查詢，以及扣款，透過事務以及 row lock 方式避免超賣
     * <p>
     * 使用 樂觀鎖 的方式避免超賣，提升併發性能
     */
    @PostMapping("/wallets/payOptimisticWrite")
    public Wallet payOptimisticWrite(@RequestBody PayRequestDto payRequestDto) {
        Wallet savedWallet = walletService.payOptimisticWrite(payRequestDto.getWalletId(), TransactionType.DEPOSIT, payRequestDto.getAmount());

        return savedWallet;
    }

    /**
     * 使用悲觀鎖 查詢錢包資料
     */
    @GetMapping("/wallets/infoPessimisticWrite/{walletId}")
    public Wallet infoPessimisticWrite(@PathVariable Integer walletId){
        return walletService.infoPessimisticWrite(walletId);
    }

    /**
     * 使用樂觀鎖 查詢錢包資料
     */
    @GetMapping("/wallets/infoOptimisticWrite/{walletId}")
    public Wallet infoOptimisticWrite(@PathVariable Integer walletId){
        return walletService.infoOptimisticWrite(walletId);
    }

    /**
     * 手動執行檢查分期付款是否到期
     */
    @GetMapping("/installment/checkInstallmentDueDate")
    public String checkInstallmentDueDate(){
        Integer finishedNum = installmentService.checkInstallmentDueDate();
        return "success: " + finishedNum + " installment";
    }
}
