package com.example.workflow.services.impl;


import com.example.workflow.entity.ProductOption;
import com.example.workflow.entity.SalesOrder;
import com.example.workflow.entity.SalesOrderDetail;
import com.example.workflow.reponsitory.ProductOptionRepository;
import com.example.workflow.reponsitory.SaleOrderDetailRepository;
import com.example.workflow.reponsitory.SalesOrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component("delegateBean")
public class OrderServiceDelegate implements JavaDelegate {

    private SalesOrderRepository salesOrderRepository;
    private SaleOrderDetailRepository saleOrderDetailRepository;
    private ProductOptionRepository productOptionRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String currentActivity = execution.getCurrentActivityId();
        Long orderId = (Long) execution.getVariable("orderId");

        log.info("Order Id: " + orderId);
        log.info("Current activity: " + currentActivity);
        switch (currentActivity) {
            case "ServiceTimeOut":
                Long newId = this.ServiceTimeOut(orderId);
                execution.setVariable("result", "Added ID: " + newId);
                break;

            case "ServiceCancel":
                Long newId1 = this.ServiceTimeOut(orderId);
                execution.setVariable("result", "Added ID: " + newId1);
                break;

            case "ServicePay":
                boolean deleted = this.ServicePay();
                execution.setVariable("result", deleted ? "Deleted Successfully" : "Delete Failed");
                break;

            case "ServiceBanking":
                boolean updated = this.ServiceBanking();
                execution.setVariable("result", updated ? "Updated Successfully" : "Update Failed");
                break;

            case "ServiceSaveDB":
                boolean updatedDB = this.ServiceSaveDB(orderId);
                execution.setVariable("result", updatedDB ? "Updated Successfully" : "Update Failed");
                break;
        }
    }
    public Long ServiceTimeOut(Long orderId) {
        SalesOrder order = salesOrderRepository.findById(orderId).orElse(null);
        order.setStatus("CANCEL");
        salesOrderRepository.save(order);
        log.info("Service TimeOut");
        return null;
    }
    public Long ServiceCancel(Long orderId) {
        SalesOrder order = salesOrderRepository.findById(orderId).orElse(null);
        order.setStatus("Cancel");
        salesOrderRepository.save(order);
        log.info("Service Cencal");
        return null;
    }
    public boolean ServicePay(){
        log.info("Service Cash");
        return false;
    }
    public boolean ServiceBanking(){
        log.info("Service Banking");
        return false;
    }

    public boolean ServiceSaveDB(Long orderId){

        SalesOrder order = salesOrderRepository.findById(orderId).orElse(null);
        List<SalesOrderDetail> orderDetails = new ArrayList<>();
        if (order != null) {
            orderDetails = saleOrderDetailRepository.findBySalesOrderId(order.getSalesOrderId());
            for (SalesOrderDetail orderDetail : orderDetails) {
                Optional<ProductOption> productOption = productOptionRepository.findById(orderDetail.getOptionId());
                productOption.get().setQuantity(productOption.get().getQuantity() - orderDetail.getQuantity());
                log.info("Product Option: " + productOption.get().getOptionId());
                productOptionRepository.save(productOption.get());

            }
            log.info("Update thanh cong");
            return true;
        }
        return false;
    }

}
