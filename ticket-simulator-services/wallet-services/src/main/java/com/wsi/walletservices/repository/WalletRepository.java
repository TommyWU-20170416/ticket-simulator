package com.wsi.walletservices.repository;

import com.wsi.walletservices.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Integer>{
}
