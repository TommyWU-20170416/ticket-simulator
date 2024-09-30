package com.wsi.walletservices.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "wallet")
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Integer walletId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "wallet_amount", nullable = false)
    private Integer walletAmount;
}
