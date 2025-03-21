package com.example.workflow.dto.admin.report.jasper.response;

import java.math.BigDecimal;

public class OrderDetail {
    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal basePrice;
    private BigDecimal totalPrice;

    public OrderDetail(BigDecimal basePrice, Long id, String name, Integer quantity, BigDecimal totalPrice) {
        this.basePrice = basePrice;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
