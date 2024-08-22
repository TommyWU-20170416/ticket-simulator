package com.wsi.ticketservices.dto;

import lombok.Data;

@Data
public class TicketPurchaseRequest {
    private String username;
    private Integer scheduleId;
    private Integer ticketCount;
}
