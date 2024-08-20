package com.wsi.ticketsimulatorservices.repository;

import com.wsi.ticketsimulatorservices.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Integer> {
}
