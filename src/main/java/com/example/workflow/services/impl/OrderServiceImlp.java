package com.example.workflow.services.impl;

import com.example.workflow.dto.admin.sale.request.SaleDetailRequest;
import com.example.workflow.dto.admin.sale.response.SaleListResponse;
import com.example.workflow.dto.admin.sale.response.SaleOrderDetailResponse;
import com.example.workflow.dto.admin.sale.response.SalesOrderResponse;
import com.example.workflow.entity.SalesOrder;
import com.example.workflow.entity.SalesOrderDetail;
import com.example.workflow.reponsitory.SaleOrderDetailRepository;
import com.example.workflow.reponsitory.SalesOrderRepository;
import com.example.workflow.services.OrderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImlp implements OrderService {

    SaleOrderDetailRepository saleOrderDetailRepository;
    SalesOrderRepository salesOrderRepository;

    @Override
    public List<SaleListResponse> getSalesOrders() {
        List<SaleListResponse> saleOrders = salesOrderRepository.findAllDTO();
        return saleOrders;
    }

    @Override
    public List<SaleOrderDetailResponse> getOderDetailById(Long salesOrderId) {
        return saleOrderDetailRepository.findSalesOrderDetailsBySalesOrderId(salesOrderId);
    }

    @Override
    public SalesOrderResponse getOrderAndDetailById(Long salesOrderId) {
        SalesOrderResponse response = salesOrderRepository.findOrderById(salesOrderId);
        List<SaleOrderDetailResponse> detailResponse = saleOrderDetailRepository.findSalesOrderDetailsBySalesOrderId(salesOrderId);
        response.setDetails(detailResponse);
        return response;
    }

    @Override
    public SalesOrderResponse getOrderAndDetailByProcessId(String ProcessId) {
        SalesOrderResponse order = salesOrderRepository.findOrderByProcessId(ProcessId);
        List<SaleOrderDetailResponse> detailResponse = saleOrderDetailRepository.findSalesOrderDetailsBySalesOrderId(order.getOrderId());
        order.setDetails(detailResponse);
        return order;
    }

    @Override
    public List<SaleListResponse> getSalesOrdersByUserId(String kcid) {
        List<SaleListResponse> salesOrders = salesOrderRepository.findAllDTOByIdUser(kcid);
        return salesOrders;
    }
}


//    @Transactional
//    @Override
//    public SalesOrder createOrder(List<SaleDetailRequest> details) {
//
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        String kcid = authentication.getName();
//
//        SalesOrder salesOrder = new SalesOrder();
//        salesOrder.setKcid(kcid);
//        salesOrder.setOrderDate(LocalDate.now());
//        salesOrder.setStatus("Đã thanh toán");
//
//        BigDecimal totalPrice = details.stream()
//                .map(d -> d.getPrice().multiply(new BigDecimal(d.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        salesOrder.setTotalPrice(totalPrice);
//
//        // Lưu đơn hàng trước để lấy ID
//        SalesOrder savedOrder = salesOrderRepository.save(salesOrder);
//        Long orderId = savedOrder.getSalesOrderId();
//
//        // Chuyển đổi SaleDetailRequest → SalesOrderDetail
//        List<SalesOrderDetail> salesOrderDetails = details.stream()
//                .map(d -> {
//                    SalesOrderDetail detail = new SalesOrderDetail();
//                    detail.setSalesOrderId(orderId);
//                    detail.setProductId(d.getProductId());
//                    detail.setQuantity(d.getQuantity());
//                    detail.setPrice(d.getPrice());
//                    detail.setOptionId(d.getOptionId());
//                    return detail;
//                })
//                .collect(Collectors.toList());
//
//        // Lưu danh sách chi tiết đơn hàng
//        saleOrderDetailRepository.saveAll(salesOrderDetails);
//
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("orderId", savedOrder.getSalesOrderId());
//        variables.put("userId", kcid);
//        variables.put("totalPrice", totalPrice);
//
//        runtimeService.startProcessInstanceByKey("orderProcess", String.valueOf(orderId), variables);
//
//        return savedOrder;
//
//    }
