package com.example.workflow.reponsitory;

import com.example.workflow.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCateRespository extends JpaRepository<SubCategory, Long> {

    List<SubCategory> findSubCategoriesByCategoryId(Long cateId);
}
