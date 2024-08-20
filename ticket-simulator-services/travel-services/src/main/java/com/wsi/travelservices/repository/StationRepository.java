package com.wsi.travelservices.repository;


import com.wsi.travelservices.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Integer> {
}
