package com.example.workflow.reponsitory;

import camundajar.impl.scala.None;
import com.example.workflow.dto.admin.products.option.response.OptionResponse;
import com.example.workflow.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query("SELECT new com.example.workflow.dto.admin.products.option.response.OptionResponse(" +
            "po.optionId, po.color, po.colorPriceAdjustment, po.storageRam, po.storagePriceAdjustment, po.quantity )" +
            "FROM ProductOption  po" +
            " WHERE po.productId = ?1")
    List<OptionResponse> findOptionByProductId(Long productId);

}
