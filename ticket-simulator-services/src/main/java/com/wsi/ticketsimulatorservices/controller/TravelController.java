package com.wsi.ticketsimulatorservices.controller;

import com.wsi.ticketsimulatorservices.dto.AttractionDto;
import com.wsi.ticketsimulatorservices.dto.RestaurantDto;
import com.wsi.ticketsimulatorservices.model.Attraction;
import com.wsi.ticketsimulatorservices.model.Restaurant;
import com.wsi.ticketsimulatorservices.model.Station;
import com.wsi.ticketsimulatorservices.repository.AttractionRepository;
import com.wsi.ticketsimulatorservices.repository.RestaurantRepository;
import com.wsi.ticketsimulatorservices.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TravelController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private StationRepository stationRepository;

    @GetMapping("/restaurants")
    public List<RestaurantDto> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantDto> restaurantDtos = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            restaurantDtos.add(convertToRestaurantDto(restaurant));
        }
        return restaurantDtos;
    }

    @GetMapping("/restaurants/{stationId}")
    public List<RestaurantDto> getRestaurantsByStation(@PathVariable Integer stationId) {
        List<Restaurant> restaurants = restaurantRepository.findByStationId(stationId);
        List<RestaurantDto> restaurantDtos = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            restaurantDtos.add(convertToRestaurantDto(restaurant));
        }
        return restaurantDtos;
    }

    @GetMapping("/attractions")
    public List<AttractionDto> getAttractions() {
        List<Attraction> attractions = attractionRepository.findAll();
        List<AttractionDto> attractionDtos = new ArrayList<>();
        for (Attraction attraction : attractions) {
            attractionDtos.add(convertToAttractionDto(attraction));
        }
        return attractionDtos;
    }

    @GetMapping("/attractions/{stationId}")
    public List<AttractionDto> getAttractionsByStation(@PathVariable Integer stationId) {
        List<Attraction> attractions = attractionRepository.findByStationId(stationId);
        List<AttractionDto> attractionDtos = new ArrayList<>();
        for (Attraction attraction : attractions) {
            attractionDtos.add(convertToAttractionDto(attraction));
        }
        return attractionDtos;
    }

    @GetMapping("/stations")
    public List<Station> getStations() {
        System.out.println("Getting stations...");
        return stationRepository.findAll();
    }

//    @PostMapping("/restaurants/uploadImage")
//    public void uploadImageToRestaurant(@RequestBody ImageUploadRequestDto imageUploadRequestDto) {
//        Restaurant restaurant = restaurantRepository.findById(imageUploadRequestDto.getRestaurantId()).orElse(null);
//        restaurant.setImageBlob(imageUploadRequestDto.getImageBase64().getBytes());
//        restaurantRepository.save(restaurant);
//        System.out.println("Uploading image to restaurant...");
//    }


    private RestaurantDto convertToRestaurantDto(Restaurant restaurant) {
        RestaurantDto dto = new RestaurantDto();
        dto.setRestaurantId(restaurant.getRestaurantId());
        dto.setRestaurantName(restaurant.getRestaurantName());
        dto.setRestaurantImageUrl(restaurant.getRestaurantImageUrl());
        dto.setStationId(restaurant.getStationId());
        dto.setRestaurantImageBlob((restaurant.getImageBlob() == null ? null : new String(restaurant.getImageBlob())));
        return dto;
    }

    private AttractionDto convertToAttractionDto(Attraction attraction) {
        AttractionDto dto = new AttractionDto();
        dto.setAttractionId(attraction.getAttractionId());
        dto.setAttractionName(attraction.getAttractionName());
        dto.setAttractionImageUrl(attraction.getAttractionImageUrl());
        dto.setStationId(attraction.getStationId());
        dto.setAttractionImageBlob((attraction.getAttractionImageBlob() == null ? null : new String(attraction.getAttractionImageBlob())));
        return dto;
    }
}
