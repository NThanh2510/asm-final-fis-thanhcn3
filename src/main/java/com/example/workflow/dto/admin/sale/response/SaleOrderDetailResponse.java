package com.example.workflow.dto.admin.sale.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class SaleOrderDetailResponse {
    String productInfo;
    Integer quantity;
    BigDecimal basePrice;
    BigDecimal priceOption;
    //String img;
    BigDecimal price;
    BigDecimal total;


    public SaleOrderDetailResponse(String productInfo, Integer quantity, BigDecimal basePrice, BigDecimal priceOption ) {
        this.productInfo = productInfo;
        this.quantity = quantity;
        this.basePrice = basePrice;
        this.priceOption = priceOption;
        this.price = basePrice.add(priceOption);
        this.total = this.price.multiply(BigDecimal.valueOf(quantity));
    }
}
