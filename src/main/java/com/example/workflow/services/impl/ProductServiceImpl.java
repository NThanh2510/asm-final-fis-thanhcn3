package com.example.workflow.services.impl;

import com.example.workflow.dto.admin.products.option.request.OptionRequest;
import com.example.workflow.dto.admin.products.request.CreateProductRequest;
import com.example.workflow.dto.admin.products.option.response.OptionResponse;
import com.example.workflow.dto.admin.products.response.*;
import com.example.workflow.entity.*;
import com.example.workflow.reponsitory.*;
import com.example.workflow.services.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

     ProductRepository productRepository;
     TechnicalSpecRepository technicalSpecRepository;
     ProductOptionRepository productOptionRepository;
     CateRespository cateRespository;
     SubCateRespository subCateRespository;

    @Override
    public CreateResponse create(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setBasePrice(request.getBasePrice());
        product.setMainImage(request.getMainImg());
        product.setCreatedDate(LocalDate.now());
        product.setWarranty(request.getWarranty());
        product.setSubCategoryId(request.getSubCategoryId());
        productRepository.save(product);

        Long Product_id = product.getProductId();
        TechnicalSpec technicalSpec = new TechnicalSpec();
        technicalSpec.setProductId(Product_id);
        technicalSpec.setScreenSize(request.getScreenSize());
        technicalSpec.setScreenResolution(request.getScreenResolution());
        technicalSpec.setProcessor(request.getProcessor());
        technicalSpec.setRearCamera(request.getRearCamera());
        technicalSpec.setFrontCamera(request.getFrontCamera());
        technicalSpec.setBattery(request.getBattery());
        technicalSpec.setFastCharging(request.isFastCharging());
        technicalSpec.setWaterResistance(request.isWater_resistance());
        technicalSpec.setOsId(request.getOs());
        technicalSpec.setDesign(request.getDesign());
        technicalSpec.setMaterial(request.getMaterial());
        technicalSpec.setWeight(request.getWeight());
        technicalSpec.setBatteryType(request.getBatteryType());
        technicalSpec.setSim(request.getSim());
        technicalSpec.setWifi(request.getWifi());
        technicalSpec.setGps(request.getGps());
        technicalSpec.setNfc(request.isNfc());
        technicalSpecRepository.save(technicalSpec);
        log.info("info: {}", technicalSpec);
        return new CreateResponse(Product_id);
    }

    @Override
    public List<ProductsResponse> getAllProduct() {
    return productRepository.findAllProducts();
    }

    @Override
    public String createOptionProduct(OptionRequest request) {
        ProductOption option = new ProductOption();
        option.setColor(request.getColor());
        option.setColorPriceAdjustment(request.getColorPriceAdjustment());
        option.setStorageRam(request.getStorageRam());
        option.setStoragePriceAdjustment(request.getStorageRamPriceAdjustment());
        option.setQuantity(request.getQuantity());
        option.setFinalPrice(request.getStorageRamPriceAdjustment().add(request.getColorPriceAdjustment()));
        option.setProductId(request.getProductId());
        productOptionRepository.save(option);
        return null;
    }

    @Override
    public ProductListOptionResponse findProductById(Long id) {
      ProductListOptionResponse response = productRepository.findByIdProductListOption(id) ;
      List<OptionResponse> options = productOptionRepository.findOptionByProductId(id);
      response.setOptions(options);
      log.info("info: {}", options);
        return response;
    }

    @Override
    public List<Category> getAllCate() {
        return cateRespository.findAll();
    }

    @Override
    public List<SubCategory> getSubCateByCateId(Long cateId) {
        return subCateRespository.findSubCategoriesByCategoryId(cateId);
    }


}
