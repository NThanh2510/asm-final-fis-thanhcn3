package com.example.workflow.reponsitory;

import com.example.workflow.dto.admin.report.jasper.response.Order;
import com.example.workflow.dto.admin.sale.response.SaleListResponse;
import com.example.workflow.dto.admin.sale.response.SalesOrderResponse;
import com.example.workflow.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    @Query("Select new com.example.workflow.dto.admin.sale.response.SaleListResponse" +
            "(s.salesOrderId, CONCAT(u.firstName, ' ',u.lastName), u.email, s.totalPrice, s.status, s.orderDate )" +
            "FROM SalesOrder s " +
            "JOIN User u ON s.kcid = u.kcid")
    List<SaleListResponse> findAllDTO();

    @Query("Select new com.example.workflow.dto.admin.report.jasper.response.Order" +
            "(s.salesOrderId, CONCAT(u.firstName, ' ',u.lastName), u.email, s.status, s.address, s.orderDate, null )" +
            "FROM SalesOrder s " +
            "JOIN User u ON s.kcid = u.kcid" +
            " WHERE s.salesOrderId = ?1")
    Order findOrderDTO(Long id);

    @Query("Select new com.example.workflow.dto.admin.sale.response.SaleListResponse" +
            "(s.salesOrderId, CONCAT(u.firstName, ' ',u.lastName), u.email, s.totalPrice, s.status, s.orderDate )" +
            "FROM SalesOrder s " +
            "JOIN User u ON s.kcid = u.kcid" +
            " WHERE u.kcid = ?1" +
            " AND s.status NOT IN ('CANCEL', 'PENDING')")
    List<SaleListResponse> findAllDTOByIdUser(String kcid);

    @Query("Select new com.example.workflow.dto.admin.sale.response.SalesOrderResponse" +
            "(s.salesOrderId, s.kcid, concat(u.firstName,' ', u.lastName), u.username, s.address, s.status,s.orderDate, s.totalPrice )" +
            "FROM SalesOrder s " +
            "JOIN User u ON s.kcid = u.kcid" +
            " WHERE s.salesOrderId = ?1")
    SalesOrderResponse findOrderById(Long id);

    @Query("Select new com.example.workflow.dto.admin.sale.response.SalesOrderResponse" +
            "(s.salesOrderId, s.kcid, concat(u.firstName,' ', u.lastName), u.username, s.address, s.status,s.orderDate, s.totalPrice )" +
            "FROM SalesOrder s " +
            "JOIN User u ON s.kcid = u.kcid" +
            " WHERE s.idprocess = ?1")
    SalesOrderResponse findOrderByProcessId(String processId);


}
