package com.wsi.walletservices.dto.request;

import lombok.Data;

@Data
public class PayRequestDto {
    private Integer walletId;
    private Integer amount;
}
