package com.wsi.ticketsimulatorservices.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "station")
@Data
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Integer stationId;

    @Column(name = "station_name")

    private String stationName;
    @Column(name = "station_index")
    private String stationIndex;
}
