package com.fsse2305.final_project.api;

import com.fsse2305.final_project.data.product.domainObject.ProductDetailsData;
import com.fsse2305.final_project.data.product.dto.response.ProductDetailsResponseDto;
import com.fsse2305.final_project.data.product.dto.response.ProductListResponseDto;
import com.fsse2305.final_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductApi {
    private final ProductService productService;

    @Autowired
    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

//    @PostMapping("/public/product")
//    public ProductDetailsResponseDto createProduct(ProductDetailsRequestDto productDetailsRequestDto){
//
//
//    }


//    @PutMapping("/public/product/{id}")

    @GetMapping("/public/product")
    public List<ProductListResponseDto> getAllProduct(){
        List<ProductDetailsData> productDetailsDataList = productService.getProductList();
        List<ProductListResponseDto> productListResponseDtoList = new ArrayList<>();
        for(ProductDetailsData productDetailsData: productDetailsDataList){
            productListResponseDtoList.add(new ProductListResponseDto(productDetailsData));
        }

        return productListResponseDtoList;
    }

    @GetMapping("/public/product/{id}")
    public ProductDetailsResponseDto getProductById(@PathVariable("id") Integer pid){
        return new ProductDetailsResponseDto(productService.getProductById(pid));
    }


}
