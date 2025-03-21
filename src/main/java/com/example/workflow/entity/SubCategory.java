package com.example.workflow.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subcategories")
@Data
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_category_id")
    private Integer subCategoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "category_id")
    private Integer categoryId;
}
