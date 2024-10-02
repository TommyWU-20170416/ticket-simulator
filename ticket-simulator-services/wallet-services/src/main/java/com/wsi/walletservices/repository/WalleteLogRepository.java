package com.wsi.walletservices.repository;

import com.wsi.walletservices.model.WalletLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalleteLogRepository extends JpaRepository<WalletLog, Integer> {
}
