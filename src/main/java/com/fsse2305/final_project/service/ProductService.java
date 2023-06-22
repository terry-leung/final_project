package com.fsse2305.final_project.service;

import com.fsse2305.final_project.data.product.domainObject.ProductDetailsData;
import com.fsse2305.final_project.data.product.domainObject.ProductRequestData;
import com.fsse2305.final_project.data.product.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductDetailsData createProduct(ProductRequestData productRequestData);

    List<ProductDetailsData> getProductList();


    ProductDetailsData getProductById(Integer pid);

    ProductEntity getProductEntityByPid(Integer pid);

    void setProductStockByEntity(ProductEntity entity, Integer quantity);
}
