package com.example.workflow.dto.admin.report.jasper.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    Long saleOrderId;
    String nameUser;
    String mail;
    String status;
    String address;
    LocalDate orderDate;
    java.util.Date orderTime;
    public Date getOrderDateAsSqlDate() {
        if (orderDate != null) {
            return Date.valueOf(orderDate);  // Chuyển đổi LocalDate thành java.sql.Date
        } else {
            return null;
        }
    }


}
