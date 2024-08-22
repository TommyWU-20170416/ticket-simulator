package com.wsi.ticketservices.service;

import com.wsi.ticketservices.dto.BuyTicketResponse;
import com.wsi.ticketservices.dto.TicketPurchaseRequest;
import com.wsi.ticketservices.model.Schedule;

public interface TicketService {

    public BuyTicketResponse buyTicket(TicketPurchaseRequest ticketPurchaseRequest);

    public Schedule updateSchedule(Schedule schedule);

    public Schedule getScheduleById(Integer scheduleId);

    public Schedule getScheduleByIdWithRedis(Integer scheduleId);

}
