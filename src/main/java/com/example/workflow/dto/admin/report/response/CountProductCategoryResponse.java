package com.example.workflow.dto.admin.report.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountProductCategoryResponse {
    Long categoryId;
    String name;
    Long count;
}
