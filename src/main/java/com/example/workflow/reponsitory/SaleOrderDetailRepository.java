package com.example.workflow.reponsitory;

import com.example.workflow.dto.admin.sale.response.SaleOrderDetailResponse;
import com.example.workflow.dto.admin.sale.response.SalesOrderResponse;
import com.example.workflow.entity.SalesOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface SaleOrderDetailRepository extends JpaRepository<SalesOrderDetail, Long> {




    @Query("SELECT NEW com.example.workflow.dto.admin.sale.response.SaleOrderDetailResponse(" +
            "CONCAT(p.name, ' ', po.color, ' ', po.storageRam), sd.quantity, p.basePrice, po.finalPrice ) " +
            " FROM SalesOrderDetail sd " +
            " JOIN SalesOrder s ON s.salesOrderId = sd.salesOrderId " +
            " JOIN ProductOption po ON sd.optionId = po.optionId" +
            " JOIN Product p ON po.productId = p.productId" +
            " WHERE s.salesOrderId = ?1" )
    List<SaleOrderDetailResponse> findSalesOrderDetailsBySalesOrderId(Long salesOrderId);


    @Query("SELECT NEW com.example.workflow.dto.admin.sale.response.SaleOrderDetailResponse(" +
            "CONCAT(p.name, ' ', po.color, ' ', po.storageRam), sd.quantity, p.basePrice, po.finalPrice ) " +
            " FROM SalesOrderDetail sd " +
            " JOIN SalesOrder s ON s.salesOrderId = sd.salesOrderId " +
            " JOIN ProductOption po ON sd.optionId = po.optionId" +
            " JOIN Product p ON po.productId = p.productId" +
            " WHERE s.idprocess = ?1" )
    List<SaleOrderDetailResponse> findSalesOrderDetailsByProcessId(String processId);

    List<SalesOrderDetail> findBySalesOrderId(Long salesOrderId);
}
