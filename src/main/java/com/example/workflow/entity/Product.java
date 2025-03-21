package com.example.workflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Column(name = "main_image")
    private String mainImage;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "warranty")
    private Integer warranty;

    @Column(name = "sub_category_id")
    private Long subCategoryId;

//    @Column(name = "promotion_id")
//    private Integer promotionId;


}
