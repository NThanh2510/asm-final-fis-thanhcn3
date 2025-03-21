package com.example.workflow.services.impl;


import com.example.workflow.dto.admin.sale.request.SaleDetailRequest;
import com.example.workflow.entity.SalesOrder;
import com.example.workflow.entity.SalesOrderDetail;
import com.example.workflow.entity.User;
import com.example.workflow.reponsitory.SaleOrderDetailRepository;
import com.example.workflow.reponsitory.SalesOrderRepository;
import com.example.workflow.reponsitory.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProcessService {
     TaskService taskService;
     RuntimeService runtimeService;
     UserRepository userRepository;
     SalesOrderRepository salesOrderRepository;
     SaleOrderDetailRepository saleOrderDetailRepository;

    public SalesOrder createOrder(List<SaleDetailRequest> details) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String kcid = authentication.getName();

        Optional<User> user = userRepository.findByKcid(kcid);
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setKcid(kcid);
        salesOrder.setOrderDate(LocalDate.now());
        salesOrder.setStatus("PENDING");

        BigDecimal totalPrice = details.stream()
                .map(d -> d.getPrice().multiply(new BigDecimal(d.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        salesOrder.setTotalPrice(totalPrice);

        // Save the order first to get its ID
        SalesOrder savedOrder = salesOrderRepository.save(salesOrder);
        Long orderId = savedOrder.getSalesOrderId();

        // Convert SaleDetailRequest → SalesOrderDetail
        List<SalesOrderDetail> salesOrderDetails = details.stream()
                .map(d -> {
                    SalesOrderDetail detail = new SalesOrderDetail();
                    detail.setSalesOrderId(orderId);
                    detail.setQuantity(d.getQuantity());
                    detail.setPrice(d.getPrice());
                    detail.setOptionId(d.getOptionId());
                    return detail;
                })
                .collect(Collectors.toList());


        saleOrderDetailRepository.saveAll(salesOrderDetails);

        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", savedOrder.getSalesOrderId());
        variables.put("userId", user.get().getUsername());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_0y5ut7e", variables);

        if (processInstance == null) {
            log.error("Failed to start the process");
            return null;
        }
        savedOrder.setIdprocess(processInstance.getId());
        salesOrderRepository.save(savedOrder);
        return savedOrder;
    }

    @Transactional
    public void approveProduct(String taskId, boolean isApproved) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("Task not found");
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("isApproved", isApproved);
        taskService.complete(taskId, variables);
    }
    @Transactional
    public void payOrder(String taskId, boolean isPay) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("Task not found");
        }

        Long orderId = (Long) runtimeService.getVariable(task.getExecutionId(), "orderId");
        log.info(" order {}", orderId);
        SalesOrder product = salesOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (isPay) {
            product.setStatus("BANKING");
        } else {
            product.setStatus("CASH");
        }
        salesOrderRepository.save(product);

        Map<String, Object> variables = new HashMap<>();
        variables.put("isPay", isPay);
        taskService.complete(taskId, variables);
    }

}
