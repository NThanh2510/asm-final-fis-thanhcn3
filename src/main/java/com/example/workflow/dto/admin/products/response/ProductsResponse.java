package com.example.workflow.dto.admin.products.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsResponse {
     Long id;
     String categoryName;
     String subCategoryName;
     String productName;
     String mainImage;
     BigDecimal basePrice;
     Integer warranty;
     LocalDate createDate;
}
