package com.wsi.walletservices.controller;

import com.wsi.walletservices.model.Wallet;
import com.wsi.walletservices.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    @Autowired
    private WalletRepository walletRepository;

    @PostMapping("/wallets")
    public Wallet insertWallet(@RequestBody Wallet wallet) {
        Wallet savedWallet = walletRepository.save(wallet);
        return savedWallet;
    }
}
