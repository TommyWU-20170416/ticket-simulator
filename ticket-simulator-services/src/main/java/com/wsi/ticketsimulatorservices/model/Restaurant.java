package com.wsi.ticketsimulatorservices.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "restaurant")
@Data
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "restaurant_image_url")
    private String restaurantImageUrl;

    @Column(name = "station_id")
    private Integer stationId;

//    @Lob // 如果沒有註解 會有這錯誤 org.postgresql.util.PSQLException: 不良的型別值 long : Lob 適用 OID 欄位
    @Column(name = "restaurant_image_blob")
    private byte[] imageBlob;
}