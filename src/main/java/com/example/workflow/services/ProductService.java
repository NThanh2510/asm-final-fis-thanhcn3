package com.example.workflow.services;

import com.example.workflow.dto.admin.products.option.request.OptionRequest;
import com.example.workflow.dto.admin.products.request.CreateProductRequest;
import com.example.workflow.dto.admin.products.response.*;
import com.example.workflow.entity.Category;
import com.example.workflow.entity.SubCategory;

import java.util.List;

public interface ProductService {
    CreateResponse create(CreateProductRequest request);
    List<ProductsResponse> getAllProduct();
    String createOptionProduct(OptionRequest request);
    ProductListOptionResponse findProductById(Long id);
    List<Category> getAllCate();
    List<SubCategory> getSubCateByCateId(Long cateId);
}
