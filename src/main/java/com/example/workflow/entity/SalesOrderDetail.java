package com.example.workflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales_order_details")
public class SalesOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_order_detail_id")
    Long salesOrderDetailId;
    Integer quantity;
    BigDecimal Price;
    @Column(name = "sub_price")
    BigDecimal subPrice;

    @Column(name = "option_id")
    Long optionId;
    @Column(name = "sales_order_id")
    Long salesOrderId;
}
