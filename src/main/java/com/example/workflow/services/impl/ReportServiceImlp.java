package com.example.workflow.services.impl;


import com.example.workflow.dto.admin.report.jasper.response.Order;
import com.example.workflow.dto.admin.report.jasper.response.OrderDetail;
import com.example.workflow.dto.admin.report.request.ReportRequest;
import com.example.workflow.dto.admin.report.request.YearRequest;
import com.example.workflow.dto.admin.report.response.CountCustomersResponse;
import com.example.workflow.dto.admin.report.response.CountProductCategoryResponse;
import com.example.workflow.dto.admin.report.response.MonthlyRevenueResponse;
import com.example.workflow.dto.admin.report.response.ReportResponse;
import com.example.workflow.dto.admin.sale.response.SaleListResponse;
import com.example.workflow.dto.admin.sale.response.SaleOrderDetailResponse;
import com.example.workflow.reponsitory.ReportRepository;
import com.example.workflow.reponsitory.SaleOrderDetailRepository;
import com.example.workflow.reponsitory.SalesOrderRepository;
import com.example.workflow.services.ReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportServiceImlp implements ReportService {

    SalesOrderRepository salesOrderRepository;
    ReportRepository reportRepository;
    private final SaleOrderDetailRepository saleOrderDetailRepository;


    @Override
    public List<ReportResponse> getRevenue(ReportRequest request) {
        return reportRepository.findReportDTOByStartDateAndEndDate(request.getStartDate(), request.getEndDate());
    }

    @Override
    public List<CountProductCategoryResponse> getCountProductCategoryByDate(ReportRequest request) {
        return reportRepository.findCountProductCategoryByDate(request.getStartDate(), request.getEndDate());
    }

    @Override
    public CountCustomersResponse getCountCustomer() {
        return reportRepository.countCustomers();
    }

    @Override
    public List<MonthlyRevenueResponse> getMonthlyRevenue(YearRequest request) {
        List<MonthlyRevenueResponse> response = reportRepository.getMonthlyRevenue(request.getYear());
        log.info(response.toString());
        return reportRepository.getMonthlyRevenue(request.getYear());
    }


    String path = "D:\\FPT IS\\Intern\\camunda\\test\\file";
    @Override
    public String exportReportOrder(Long id,  String reportFormat) throws FileNotFoundException, JRException {
        //tạo JasperReport
        String jasperFilePath = "D:\\FPT IS\\Intern\\camunda\\test\\camunda_demo_asm\\src\\main\\resources\\bill.jasper";
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(ResourceUtils.getFile(jasperFilePath));
        //lấy thông tin người dùng
        Order order = salesOrderRepository.findOrderDTO(id);
        Date orderDate = order.getOrderDateAsSqlDate();
        order.setOrderTime(orderDate);
        JRBeanCollectionDataSource userDataSource = new JRBeanCollectionDataSource(Collections.singletonList(order));
        //lấy danh sách sản phẩm theo id hóa đơn
        List<SaleOrderDetailResponse> ordersProducts = saleOrderDetailRepository.findSalesOrderDetailsBySalesOrderId(id);

        JRBeanCollectionDataSource tableProductOrder = new JRBeanCollectionDataSource(ordersProducts);

        Map<String, Object> paramaters = new HashMap<>();
        paramaters.put("table_product", tableProductOrder);
        paramaters.put("order_date", orderDate);
        //đẩy dữ liệu vào report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramaters, userDataSource);
//        JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\bill.html");

        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\bill.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\bill.pdf");
        }
        if (reportFormat.equalsIgnoreCase("excel")) {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path + "\\bill.xls"));
            exporter.exportReport();
        }
        return "report generated in path: " + reportFormat;
    }
}
