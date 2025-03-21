package com.example.workflow.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "otp_codes")
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "email")
    String email;
    @Column(name = "otp_code")
    String otpCode;
    @Column(name = "expiration_time")
    LocalDateTime expirationTime;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "used")
    boolean used;

}
