package com.wsi.ticketservices.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@Data
public class Schedule {

    @Id
    @Column(name = "schedule_id")
    private Integer scheduleId;

    @Column(name = "schedule_name")
    private String scheduleName;

    @Column(name = "schedule_tickettotal")
    private Integer scheduleTicketTotal;

    @Column(name = "schedule_ticketless")
    private Integer scheduleTicketLess;

    @Column(name = "schedule_departure_time")
    private LocalDateTime scheduleDepartureTime;

    @Column(name = "schedule_arrival_time")
    private LocalDateTime scheduleArrivalTime;

    @ManyToOne
    @JoinColumn(name = "schedule_departure_station_id", referencedColumnName = "station_id")
    private Station departureStation;

    @ManyToOne
    @JoinColumn(name = "schedule_arrival_station_id", referencedColumnName = "station_id")
    private Station arrivalStation;
}
