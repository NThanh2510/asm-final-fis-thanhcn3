package com.example.workflow.restController.admin;


import com.example.workflow.dto.ApiResponse;
import com.example.workflow.dto.admin.report.request.ReportRequest;
import com.example.workflow.dto.admin.report.request.YearRequest;
import com.example.workflow.dto.admin.report.response.CountCustomersResponse;
import com.example.workflow.dto.admin.report.response.CountProductCategoryResponse;
import com.example.workflow.dto.admin.report.response.MonthlyRevenueResponse;
import com.example.workflow.dto.admin.report.response.ReportResponse;
import com.example.workflow.services.ProductService;
import com.example.workflow.services.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/v1/admin/report")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportController {

    ReportService reportService;
    ProductService productService;

    @Operation(summary = "get revenue by date")
    @PostMapping("/revenue")
    public ApiResponse<List<ReportResponse>> getRevenue(@RequestBody @Valid ReportRequest request) {
        return ApiResponse.<List<ReportResponse>>builder()
                .code(200)
                .result(reportService.getRevenue(request))
                .message("Ok")
                .build();
    }

    @Operation(summary = "count the number of products a bran sells by date")
    @PostMapping("/count-product-category-ByDate")
    public ApiResponse<List<CountProductCategoryResponse>> getCountProductCategoryByDate(@RequestBody @Valid ReportRequest request) {
        return ApiResponse.<List<CountProductCategoryResponse>>builder()
                .code(200)
                .result(reportService.getCountProductCategoryByDate(request))
                .message("Ok")
                .build();
    }

    @Operation(summary = "count user")
    @PostMapping("/count-customers")
    public ApiResponse<CountCustomersResponse> getCountCustomers() {
        return ApiResponse.<CountCustomersResponse>builder()
                .code(200)
                .result(reportService.getCountCustomer())
                .message("Ok")
                .build();
    }

    @Operation(summary = "get total revenue by month of the year")
    @PostMapping("/MonthlyRevenue")
    public ApiResponse<List<MonthlyRevenueResponse>> getMonthlyRevenue(@RequestBody YearRequest request) {
        return ApiResponse.<List<MonthlyRevenueResponse>>builder()
                .code(200)
                .result(reportService.getMonthlyRevenue(request))
                .message("success")
                .build();
    }

    @Operation(summary = "export bill by orderId excel")
    @GetMapping("/export/bill/{format}/{id}")
    public ApiResponse<String> getMonthlyRevenue(@PathVariable Long id, @PathVariable String format) throws JRException, FileNotFoundException {
        reportService.exportReportOrder(id, format);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Ok")
                .build();
    }

}
