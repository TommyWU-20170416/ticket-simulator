package com.wsi.ticketsimulatorservices.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "attraction")
@Data
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attraction_id")
    private Integer attractionId;

    @Column(name = "attraction_name")
    private String attractionName;

    @Column(name = "attraction_image_url")
    private String attractionImageUrl;

    @Column(name = "station_id")
    private Integer stationId;

    @Column(name = "attraction_image_blob")
    private byte[] attractionImageBlob;
}