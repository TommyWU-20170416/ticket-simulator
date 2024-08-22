package com.wsi.ticketservices.repository;

import com.wsi.ticketservices.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// find all Is not Order
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query("SELECT s FROM Schedule s ORDER BY s.scheduleId ASC")
    List<Schedule> findAllOrderedByScheduleId();
}