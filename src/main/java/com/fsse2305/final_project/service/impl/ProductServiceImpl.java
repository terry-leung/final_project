package com.fsse2305.final_project.service.impl;

import com.fsse2305.final_project.data.product.domainObject.ProductDetailsData;
import com.fsse2305.final_project.data.product.domainObject.ProductRequestData;
import com.fsse2305.final_project.data.product.entity.ProductEntity;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import com.fsse2305.final_project.exception.ExceedMaximumStockException;
import com.fsse2305.final_project.exception.ProductModifyingException;
import com.fsse2305.final_project.exception.ProductNotFoundException;
import com.fsse2305.final_project.repository.ProductRepository;
import com.fsse2305.final_project.service.ProductService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @Override
//    public ProductDetailsData createProduct(ProductRequestData productRequestData) {
//            if (productRequestData.getName() == null ||
//                    productRequestData.getPrice() == null ||
//                    productRequestData.getStock() == null) {
//                logger.warn("Create product failed: Data missing");
//                throw new ProductModifyingException();
//            }
//            if (productRequestData.getPrice().compareTo(BigDecimal.valueOf(0)) < 0) {
//                logger.warn("Create product failed: Invalid price");
//                throw new ProductModifyingException();
//            }
//            if (productRequestData.getStock() < 0) {
//                logger.warn("Create product failed: Invalid stock number");
//                throw new ProductModifyingException();
//            }
//            ProductEntity productEntity = new ProductEntity(productRequestData);
//
//            return new ProductDetailsData(productRepository.save(productEntity));
//
//    }

    @Override
    public List<ProductDetailsData> getProductList(){
        List<ProductDetailsData> productDetailsDataList = new ArrayList<>();
        for(ProductEntity productEntity: productRepository.findAll()){
            productDetailsDataList.add(new ProductDetailsData(productEntity));
        }

        return productDetailsDataList;
    }

    @Override
    public ProductDetailsData getProductById(Integer pid){
        try {
            return new ProductDetailsData(getProductEntityByPid(pid));
        } catch (ProductNotFoundException ex) {
            logger.warn("Product not found");
            throw ex;
        }
    }

    @Override
    public ProductEntity getProductEntityByPid(Integer pid){
        Optional<ProductEntity> optionalEntity = productRepository.findById(pid);
        if (optionalEntity.isEmpty()){
            logger.warn("Product not found");
            throw new ProductNotFoundException();
        }
        return optionalEntity.get();
//        *** another method (without logging) ***
//        return productRepository.findById(pid).orElseThrow(() -> new ProductNotFoundException());
//        return productRepository.findById(pid).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public void setProductStockByEntity(ProductEntity entity, Integer quantity){
        if(entity.getStock() < quantity){
            logger.warn("Order Quantity Exceeds Available Stock");
            throw new ExceedMaximumStockException();
        }
        productRepository.setProductStockByPid(entity.getPid(), quantity);
    }


}
