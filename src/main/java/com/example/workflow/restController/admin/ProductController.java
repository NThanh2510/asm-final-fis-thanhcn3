package com.example.workflow.restController.admin;

import com.example.workflow.dto.ApiResponse;
import com.example.workflow.dto.admin.products.option.request.OptionRequest;
import com.example.workflow.dto.admin.products.request.CreateProductRequest;
import com.example.workflow.dto.admin.products.option.response.OptionResponse;
import com.example.workflow.dto.admin.products.response.CreateResponse;
import com.example.workflow.dto.admin.products.response.ProductListOptionResponse;
import com.example.workflow.dto.admin.products.response.ProductsResponse;
import com.example.workflow.entity.Category;
import com.example.workflow.entity.SubCategory;
import com.example.workflow.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping ("/api/v1/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

     ProductService productService;

   @PostMapping("/product/create")
    public ApiResponse<CreateResponse> createProduct(@RequestBody @Valid CreateProductRequest request) {
       return ApiResponse.<CreateResponse>builder()
               .code(200)
               .result(productService.create(request))
               .message("success")
               .build();
    }

    @Operation(summary = "get list product")
    @GetMapping("/products")
    public ApiResponse<List<ProductsResponse>> getAllProducts() {
        return ApiResponse.<List<ProductsResponse>>builder()
                .code(200)
                .result(productService.getAllProduct())
                .message("success")
                .build();
    }

    @Operation(summary = "create option ")
    @PostMapping("/option/create")
    public ApiResponse<String> createVoucher(@RequestBody @Valid OptionRequest request) {
        return ApiResponse.<String>builder()
                .code(200)
                .result(productService.createOptionProduct(request))
                .message("success")
                .build();
    }


    @Operation(summary = "get product and list option by productId")
    @GetMapping("/product/{id}/option")
    public ApiResponse<ProductListOptionResponse> getProductById(@PathVariable Long id) {
        return ApiResponse.<ProductListOptionResponse>builder()
                .code(200)
                .result(productService.findProductById(id))
                .message("success")
                .build();
    }

    @Operation(summary = "get list category")
    @GetMapping("/cate-all")
    public ApiResponse<List<Category>> getAllCate() {
        return ApiResponse.<List<Category>>builder()
                .code(200)
                .result(productService.getAllCate())
                .message("success")
                .build();
    }

    @Operation(summary = "get list subcategory by category")
    @GetMapping("/sub-cate/{id}")
    public ApiResponse<List<SubCategory>> getAllCate(@PathVariable Long id) {
        return ApiResponse.<List<SubCategory>>builder()
                .code(200)
                .result(productService.getSubCateByCateId(id))
                .message("success")
                .build();
    }

}
