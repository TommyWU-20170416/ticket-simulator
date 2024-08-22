package com.wsi.ticketservices.dto;

import lombok.Data;

@Data
public class BuyTicketResponse {
    private Integer scheduleTicketLess;

    public BuyTicketResponse(Integer scheduleTicketLess) {
        this.scheduleTicketLess = scheduleTicketLess;
    }
}
