package com.wsi.walletservices.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "public")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "wallet_id")
    private Integer walletId;
}
