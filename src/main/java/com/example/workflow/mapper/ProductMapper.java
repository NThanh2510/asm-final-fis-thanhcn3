package com.example.workflow.mapper;

import com.example.workflow.dto.admin.products.response.ProductResponseInfo;
import com.example.workflow.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseInfo toProductResponseInfo(Product product);
}
