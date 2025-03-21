package com.example.workflow.dto.admin.products.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProductOptionRequest {
    String color;
    BigDecimal colorPriceAdjustment;
    String storageRam;
    BigDecimal storagePriceAdjustment;
    Long quantity;
    BigDecimal finalPrice;
    Long productId;
}
