package com.example.workflow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "sales_orders")
@Data
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_order_id")
    private Long salesOrderId;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "status")
    private String status;

    @Column(name = "address")
    private String address;


    @Temporal(TemporalType.DATE)
    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "kcid")
    private String kcid;

    @Column(name = "idprocess")
    private String idprocess;

}
