package com.example.workflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter


@NoArgsConstructor
@Table(name = "product_options")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    Long optionId;
    String color;
    @Column(name = "color_price_adjustment")
    BigDecimal colorPriceAdjustment;
    @Column(name = "storage_ram")
    String storageRam;
    @Column(name = "storage_price_adjustment")
    BigDecimal storagePriceAdjustment;
    Integer quantity;
    @Column(name = "final_price")
    BigDecimal finalPrice;
    @Column(name = "product_id")
    Long productId;

    public ProductOption(Long optionId, String color, BigDecimal colorPriceAdjustment, String storageRam, BigDecimal storagePriceAdjustment, Integer quantity,Long productId) {
        this.optionId = optionId;
        this.color = color;
        this.colorPriceAdjustment = colorPriceAdjustment;
        this.storageRam = storageRam;
        this.storagePriceAdjustment = storagePriceAdjustment;
        this.quantity = quantity;
        this.finalPrice = colorPriceAdjustment.add(storagePriceAdjustment);
        this.productId = productId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setColorPriceAdjustment(BigDecimal colorPriceAdjustment) {
        this.colorPriceAdjustment = colorPriceAdjustment;
    }

    public void setStorageRam(String storageRam) {
        this.storageRam = storageRam;
    }

    public void setStoragePriceAdjustment(BigDecimal storagePriceAdjustment) {
        this.storagePriceAdjustment = storagePriceAdjustment;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = colorPriceAdjustment.add(storagePriceAdjustment);
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
