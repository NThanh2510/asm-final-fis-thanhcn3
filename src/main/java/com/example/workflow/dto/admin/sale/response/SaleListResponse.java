package com.example.workflow.dto.admin.sale.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleListResponse {
    Long saleOrderId;
    String nameUser;
    String mail;
    BigDecimal price;
    String status;
    LocalDate date;

}
