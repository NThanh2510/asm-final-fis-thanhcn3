package com.example.workflow.services;
import com.example.workflow.dto.admin.sale.request.SaleDetailRequest;
import com.example.workflow.dto.admin.sale.response.SaleListResponse;
import com.example.workflow.dto.admin.sale.response.SaleOrderDetailResponse;
import com.example.workflow.dto.admin.sale.response.SalesOrderResponse;
import com.example.workflow.entity.SalesOrder;
import jakarta.transaction.Transactional;

import java.util.List;


public interface OrderService {
     List<SaleListResponse> getSalesOrders();
     List<SaleOrderDetailResponse> getOderDetailById(Long salesOrderId);
     SalesOrderResponse getOrderAndDetailById(Long salesOrderId);
     SalesOrderResponse getOrderAndDetailByProcessId(String ProcessId);
     List<SaleListResponse> getSalesOrdersByUserId(String kcid);
}
