package com.wsi.walletservices.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_log", schema = "public")
@Data
public class WalletLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "wallet_id", nullable = false)
    private Integer walletId;

    @Column(name = "wallet_transaction", nullable = false)
    private Integer walletTransaction;

    @Column(name = "amount_transaction", nullable = false)
    private Integer amountTransaction;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
