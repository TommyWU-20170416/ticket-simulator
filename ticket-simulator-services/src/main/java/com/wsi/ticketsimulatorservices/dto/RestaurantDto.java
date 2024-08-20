package com.wsi.ticketsimulatorservices.dto;

import lombok.Data;

/**
 * Data transfer object for Restaurant
 * In order to transfer restaurantImageBlob from byte[] to String, we need to convert it to Base64
 */
@Data
public class RestaurantDto {
    private Integer restaurantId;
    private String restaurantName;
    private String restaurantImageUrl;
    private Integer stationId;
    private String restaurantImageBlob;
}
