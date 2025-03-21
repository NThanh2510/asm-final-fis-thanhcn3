package com.example.workflow.services;



import com.example.workflow.dto.admin.report.request.ReportRequest;
import com.example.workflow.dto.admin.report.request.YearRequest;
import com.example.workflow.dto.admin.report.response.CountCustomersResponse;
import com.example.workflow.dto.admin.report.response.CountProductCategoryResponse;
import com.example.workflow.dto.admin.report.response.MonthlyRevenueResponse;
import com.example.workflow.dto.admin.report.response.ReportResponse;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface ReportService {

    List<ReportResponse> getRevenue(ReportRequest request);

    List<CountProductCategoryResponse> getCountProductCategoryByDate(ReportRequest request);

    CountCustomersResponse getCountCustomer();

    List<MonthlyRevenueResponse> getMonthlyRevenue(YearRequest request);

    String exportReportOrder(Long id,  String reportFormat) throws FileNotFoundException, JRException;

}
