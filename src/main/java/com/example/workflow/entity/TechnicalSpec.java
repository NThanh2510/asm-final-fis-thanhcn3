package com.example.workflow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "technical_specs")
@Data
public class TechnicalSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technical_specs_id")
    private Integer technicalSpecsId;

    @Column(name = "screen_size")
    private String screenSize;

    @Column(name = "screen_resolution")
    private String screenResolution;

    @Column(name = "processor")
    private String processor;

    @Column(name = "rear_camera")
    private String rearCamera;

    @Column(name = "front_camera")
    private String frontCamera;

    @Column(name = "battery")
    private String battery;

    @Column(name = "fast_charging")
    private Boolean fastCharging;

    @Column(name = "water_resistance")
    private Boolean waterResistance;

    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "product_id")
    private Long productId; // Chỉ lưu trữ ID của `Product`

    @Column(name = "os")
    private Long osId; // Chỉ lưu trữ ID của `OS`

    @Column(name = "design")
    private String design; // Chỉ lưu trữ ID của `Connectivity`

    @Column(name = "material")
    private String material;

    @Column(name = "weight")
    private double weight;

    @Column(name = "battery_type")
    private String batteryType;

    @Column(name = "network")
    private String network;

    @Column(name = "sim")
    private String sim;

    @Column(name = "wifi")
    private String wifi;

    @Column(name = "gps")
    private String gps;

    @Column(name = "nfc")
    private Boolean nfc;

    
}
