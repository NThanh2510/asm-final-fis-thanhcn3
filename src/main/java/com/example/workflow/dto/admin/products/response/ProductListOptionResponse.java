package com.example.workflow.dto.admin.products.response;

import com.example.workflow.dto.admin.products.option.response.OptionResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductListOptionResponse {

    private Long productId;
    private String name;
    private BigDecimal basePrice;
    private String mainImg;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d/M/yyyy")
    private LocalDate createDate;
    private Integer warranty;
    private String subCategoryName;
    private String categoryName;
    private String screenSize;
    private String screenResolution;
    private String processor;
    private String rearCamera;
    private String frontCamera;
    private String battery;
    private boolean fastCharging;
    private boolean water_resistance;
    private LocalDate releaseDate;
    private String os;
    private String design;
    private  String material;
    private double weight;
    private  String batteryType;
    private  String network;
    private  String sim;
    private String wifi;
    private String gps;
    private boolean nfc;
    private List<OptionResponse> options;


}
