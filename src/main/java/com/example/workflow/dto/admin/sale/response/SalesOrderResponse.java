package com.example.workflow.dto.admin.sale.response;

import com.example.workflow.entity.SalesOrder;
import com.example.workflow.entity.SalesOrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.N;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter

public class SalesOrderResponse {
    private Long orderId;
    private String kcid;
    private String name;
    private String username;
    private String address;
    private String status;
    private LocalDate orderDate;
    private BigDecimal totalPrice;
    private List<SaleOrderDetailResponse> details;

    public SalesOrderResponse(Long orderId,String kcid, String name, String username, String address,  String status,LocalDate orderDate, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.kcid = kcid;
        this.name = name;
        this.username = username;
        this.status = status;
        this.address = address;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
}
