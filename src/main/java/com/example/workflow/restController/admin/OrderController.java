package com.example.workflow.restController.admin;


import com.example.workflow.dto.ApiResponse;
import com.example.workflow.dto.admin.sale.request.SaleRequest;
import com.example.workflow.dto.admin.sale.response.SaleListResponse;
import com.example.workflow.dto.admin.sale.response.SaleOrderDetailResponse;
import com.example.workflow.dto.admin.sale.response.SalesOrderResponse;
import com.example.workflow.entity.SalesOrder;
import com.example.workflow.services.OrderService;
import com.example.workflow.services.impl.ProcessService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

     OrderService salesOrderService;
     ProcessService processService;

    @Operation(summary = "get list order")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    public ApiResponse<List<SaleListResponse>> listOrders() {
        return ApiResponse.<List<SaleListResponse>>builder()
                .code(200)
                .result(salesOrderService.getSalesOrders())
                .message("Ok")
                .build();
    }

    @Operation(summary = "get order detail by id")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/order-detail/{id}")
    public ApiResponse<List<SaleOrderDetailResponse>> orderDetail(@PathVariable Long id) {
        return ApiResponse.<List<SaleOrderDetailResponse>>builder()
                .code(200)
                .result(salesOrderService.getOderDetailById(id))
                .message("Ok")
                .build();
    }

    @Operation(summary = "get order and list order detail by orderId")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("order-and-detail/{id}")
    public ApiResponse<SalesOrderResponse> getOrderDetailFull(@PathVariable Long id) {
        return ApiResponse.<SalesOrderResponse>builder()
                .code(200)
                .result(salesOrderService.getOrderAndDetailById(id))
                .message("Ok")
                .build();
    }

    @Operation(summary = "get order and list order detail by orderId in taskList")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("order-and-detail-process/{processId}")
    public ApiResponse<SalesOrderResponse> getOrderDetailFullByProcess(@PathVariable String processId) {
        return ApiResponse.<SalesOrderResponse>builder()
                .code(200)
                .result(salesOrderService.getOrderAndDetailByProcessId(processId))
                .message("Ok")
                .build();
    }

    @Operation(summary = "get list order by user")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/order-user/{id}")
    public ApiResponse<List<SaleListResponse>> ordersByUserId(@PathVariable String id) {
        return ApiResponse.<List<SaleListResponse>>builder()
                .code(200)
                .result(salesOrderService.getSalesOrdersByUserId(id))
                .message("Ok")
                .build();
    }

    @Operation(summary = "start process buy product")
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/order/start")
    public ApiResponse<String> submitOrder(@RequestBody SaleRequest request) {
        SalesOrder processInstance = processService.createOrder(request.getDetails());
        return ApiResponse.<String>builder()
                .result("")
                .message("Ok")
                .build();
    }

    @Operation(summary = "approve userTask order")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/order/{taskId}/approve")
    public ApiResponse<String> approveOrder(@PathVariable String taskId, @RequestParam boolean isApproved) {
        processService.approveProduct(taskId, isApproved);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Product approval completed")
                .build();
    }

    @Operation(summary = "approve userTask banking or cash")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/order/{taskId}/pay")
    public ApiResponse<String> payOrder(@PathVariable String taskId, @RequestParam boolean isPay) {
        processService.payOrder(taskId, isPay);
        return ApiResponse.<String>builder()
                .result("")
                .message("Product Pay completed")
                .build();
    }

}
