package com.wsi.ticketsimulatorservices.dto;

import lombok.Data;

@Data
public class ImageUploadRequestDto {
    private Integer restaurantId;
    private String imageBase64;
}
