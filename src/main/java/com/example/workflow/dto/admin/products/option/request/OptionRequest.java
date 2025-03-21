package com.example.workflow.dto.admin.products.option.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OptionRequest {
    String color;
    BigDecimal colorPriceAdjustment;
    String storageRam;
    BigDecimal storageRamPriceAdjustment;
    Integer quantity;
    Long productId;
}
