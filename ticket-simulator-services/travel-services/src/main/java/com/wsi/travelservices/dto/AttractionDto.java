package com.wsi.travelservices.dto;

import lombok.Data;

/**
 * Data transfer object for Restaurant
 * In order to transfer restaurantImageBlob from byte[] to String, we need to convert it to Base64
 */
@Data
public class AttractionDto {
    private Integer attractionId;
    private String attractionName;
    private String attractionImageUrl;
    private Integer stationId;
    private String attractionImageBlob;
}
