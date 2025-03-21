package com.example.workflow.dto.admin.report.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    Integer Year;
    Integer Month;
    Integer Day;
    Long countOrders;
    BigDecimal totalRevenue;
}
