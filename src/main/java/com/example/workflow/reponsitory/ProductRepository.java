package com.example.workflow.reponsitory;
import com.example.workflow.dto.admin.products.response.ProductListOptionResponse;
import com.example.workflow.dto.admin.products.response.ProductResponseInfo;
import com.example.workflow.dto.admin.products.response.ProductsResponse;
import com.example.workflow.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

@Query("SELECT new com.example.workflow.dto.admin.products.response.ProductListOptionResponse(" +
        "p.productId, p.name, p.basePrice, p.mainImage, p.createdDate, p.warranty, " +
        "sc.name, c.name, ts.screenSize, ts.screenResolution, ts.processor, ts.rearCamera," +
        "ts.frontCamera, ts.battery,ts.fastCharging, ts.waterResistance, ts.releaseDate, os.name," +
        "ts.design, ts.material, ts.weight, ts.batteryType, ts.network, ts.sim, ts.wifi," +
        "ts.gps, ts.nfc, null  " +
        ")" +
        "FROM Product p " +
        " FULL JOIN ProductImage pi ON p.productId = pi.productId" +
        " FULL JOIN TechnicalSpec ts on p.productId = ts.productId" +
        " FULL JOIN SubCategory sc on p.subCategoryId = sc.subCategoryId" +
        "  FULL JOIN Category c on sc.categoryId = c.categoryId" +
        " FULL JOIN Os os on os.id = ts.osId" +
        " WHERE p.productId = ?1 ")
ProductListOptionResponse findByIdProductListOption(Long id);

    @Query("SELECT new com.example.workflow.dto.admin.products.response.ProductsResponse(" +
            "p.productId, c.name, sc.name, p.name, p.mainImage, p.basePrice, p.warranty, p.createdDate)" +
            " FROM Product p" +
            " JOIN SubCategory sc on sc.subCategoryId = p.subCategoryId" +
            " JOIN Category c on c.categoryId = sc.categoryId"
           )
    List<ProductsResponse> findAllProducts();



}
