package com.example.workflow.reponsitory;

import com.example.workflow.entity.Category;
import com.example.workflow.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CateRespository extends JpaRepository<Category, Long> {
    List<SubCategory> findSubCategoriesByCategoryId(Long categoryId);
}
