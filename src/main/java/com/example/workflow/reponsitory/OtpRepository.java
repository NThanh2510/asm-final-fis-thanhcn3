package com.example.workflow.reponsitory;

import com.example.workflow.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OTP, Long> {
    @Query("select o from OTP o WHERE o.email = ?1")
    List<OTP> findByEmail(String email);
}
