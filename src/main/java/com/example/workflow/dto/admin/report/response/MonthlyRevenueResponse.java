package com.example.workflow.dto.admin.report.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyRevenueResponse {
    Integer monthly;
    BigDecimal revenue;
}
