package com.wsi.travelservices.repository;

import com.wsi.travelservices.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Query("SELECT r FROM Restaurant r WHERE r.stationId = :stationId")
    List<Restaurant> findByStationId(@Param("stationId") Integer stationId);
}

