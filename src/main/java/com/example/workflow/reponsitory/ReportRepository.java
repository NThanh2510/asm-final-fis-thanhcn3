package com.example.workflow.reponsitory;

import com.example.workflow.dto.admin.report.response.CountCustomersResponse;
import com.example.workflow.dto.admin.report.response.CountProductCategoryResponse;
import com.example.workflow.dto.admin.report.response.MonthlyRevenueResponse;
import com.example.workflow.dto.admin.report.response.ReportResponse;
import com.example.workflow.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends JpaRepository<SalesOrder, Long>  {
    @Query("Select new com.example.workflow.dto.admin.report.response.ReportResponse(" +
            "EXTRACT(YEAR FROM s.orderDate)," +
            "EXTRACT(MONTH FROM s.orderDate)," +
            "EXTRACT(DAY FROM s.orderDate)," +
            "COUNT(s.salesOrderId)," +
            "SUM(s.totalPrice) " +
            ")" +
            "FROM SalesOrder s " +
            "WHERE " +
            "s.status NOT LIKE 'CANCEL'" +
            " AND s.status NOT LIKE 'PENDING'" +
            "AND s.orderDate >= :startDate " +
            "AND s.orderDate <= :endDate " +
            " GROUP BY " +
            "EXTRACT(YEAR FROM s.orderDate)," +
            "EXTRACT(MONTH FROM s.orderDate)," +
            "EXTRACT(DAY FROM s.orderDate)" +
            "ORDER BY " +
            "EXTRACT(YEAR FROM s.orderDate) DESC , " +
            "EXTRACT(MONTH FROM s.orderDate) DESC , " +
            "EXTRACT(DAY FROM s.orderDate) desc ")
    List<ReportResponse> findReportDTOByStartDateAndEndDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.example.workflow.dto.admin.report.response.CountProductCategoryResponse(" +
            "c.categoryId, c.name, count(sod.quantity)" +
            ")" +
            " FROM SalesOrderDetail sod" +
            " JOIN SalesOrder so on so.salesOrderId = sod.salesOrderId" +
            " JOIN ProductOption po on po.optionId = sod.optionId" +
            " JOIN Product  p on p.productId = po.productId" +
            " JOIN SubCategory sc on sc.subCategoryId = p.subCategoryId" +
            " JOIN Category c on c.categoryId = sc.categoryId" +
            " WHERE so.orderDate > :startDate" +
            " AND so.orderDate < :endDate" +
            " AND so.status NOT LIKE 'CANCEL'" +
            " AND so.status NOT LIKE 'PENDING'" +
            " GROUP BY c.categoryId")
    List<CountProductCategoryResponse> findCountProductCategoryByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query("SELECT new com.example.workflow.dto.admin.report.response.CountCustomersResponse (" +
            "COUNT(DISTINCT kcid))" +
            "FROM User ")
    CountCustomersResponse countCustomers();

    @Query("SELECT new com.example.workflow.dto.admin.report.response.MonthlyRevenueResponse(" +
            "EXTRACT(MONTH FROM so.orderDate), SUM(so.totalPrice))" +
            " FROM SalesOrder so" +
            " WHERE EXTRACT(YEAR FROM so.orderDate) = :year" +
            " AND so.status NOT LIKE 'CANCEL'" +
            " AND so.status NOT LIKE 'PENDING'" +
            " GROUP BY EXTRACT(MONTH FROM so.orderDate)" +
            " ORDER BY  EXTRACT(MONTH FROM so.orderDate)")
    List<MonthlyRevenueResponse> getMonthlyRevenue(Integer year);
}
