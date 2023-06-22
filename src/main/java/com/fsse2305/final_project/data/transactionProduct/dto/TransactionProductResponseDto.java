package com.fsse2305.final_project.data.transactionProduct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2305.final_project.data.product.domainObject.ProductDetailsData;
import com.fsse2305.final_project.data.product.dto.response.ProductDetailsResponseDto;
import com.fsse2305.final_project.data.product.entity.ProductEntity;
import com.fsse2305.final_project.data.transaction.dto.response.TransactionResponseDto;
import com.fsse2305.final_project.data.transaction.entity.TransactionEntity;
import com.fsse2305.final_project.data.transactionProduct.domainObject.TransactionProductDetailData;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class TransactionProductResponseDto {
    private Integer tpid;
//    private Integer tid;
    private ProductDetailsResponseDto product;
    private Integer quantity;
    private BigDecimal subtotal;

    public TransactionProductResponseDto(TransactionProductDetailData data){
        this.tpid = data.getTpid();
//        this.tid = data.getTid();
        this.product = new ProductDetailsResponseDto(data.getPid(), data.getName(), data.getDescription(), data.getImageUrl(), data.getPrice(), data.getStock());
        this.quantity = data.getQuantity();
        this.subtotal = data.getSubtotal();

    }
    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

//    public Integer getTid() {
//        return tid;
//    }
//
//    public void setTid(Integer tid) {
//        this.tid = tid;
//    }

    public ProductDetailsResponseDto getProduct() {
        return product;
    }

    public void setProduct(ProductDetailsResponseDto product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
