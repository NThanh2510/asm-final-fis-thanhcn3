package com.example.workflow.reponsitory;

import com.example.workflow.entity.TechnicalSpec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicalSpecRepository extends JpaRepository<TechnicalSpec, Integer> {
}
