package com.wsi.travelservices.repository;


import com.wsi.travelservices.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
    @Query("SELECT a FROM Attraction a WHERE a.stationId = :stationId")
    List<Attraction> findByStationId(@Param("stationId") Integer stationId);
}
