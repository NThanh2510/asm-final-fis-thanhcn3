package com.example.workflow.dto.admin.sale.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaleDetailRequest {
    Integer quantity;
    BigDecimal price;
    BigDecimal subPrice;
    Long optionId;
    Long saleOrderId;

}
