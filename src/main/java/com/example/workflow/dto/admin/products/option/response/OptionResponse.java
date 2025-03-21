package com.example.workflow.dto.admin.products.option.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data

public class OptionResponse {
    Long optionId;
    String color;
    BigDecimal colorPriceAdjustment;
    String storageRam;
    BigDecimal storageRamPriceAdjustment;
    Integer quantity;
    BigDecimal finalPrice;

    public OptionResponse(Long optionId, String color, BigDecimal colorPriceAdjustment,
                          String storageRam, BigDecimal storageRamPriceAdjustment, Integer quantity) {
        this.optionId = optionId;
        this.color = color;
        this.colorPriceAdjustment = colorPriceAdjustment;
        this.storageRam = storageRam;
        this.storageRamPriceAdjustment = storageRamPriceAdjustment;
        this.quantity = quantity;
        this.finalPrice =colorPriceAdjustment.add(storageRamPriceAdjustment);
    }

    public OptionResponse() {
    }
}
