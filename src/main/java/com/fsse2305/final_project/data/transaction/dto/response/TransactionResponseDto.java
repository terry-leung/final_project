package com.fsse2305.final_project.data.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fsse2305.final_project.data.transaction.TransactionStatus;
import com.fsse2305.final_project.data.transaction.domainObject.TransactionDetailData;
import com.fsse2305.final_project.data.transactionProduct.domainObject.TransactionProductDetailData;
import com.fsse2305.final_project.data.transactionProduct.dto.TransactionProductResponseDto;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@JsonPropertyOrder({"tid", "buyer_uid", "datetime", "status", "total", "items"})
public class TransactionResponseDto {
    private Integer tid;
    @JsonProperty("buyer_uid")
    private Integer buyerUid;
    private String datetime;
    private TransactionStatus status;
    private BigDecimal total;
    @JsonProperty("items")
    private List<TransactionProductResponseDto> transactionProducts;

    public TransactionResponseDto(TransactionDetailData data){
        this.tid = data.getTid();
        this.buyerUid = data.getUser().getUid();
        this.datetime = data.getDatetime().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HH:mm:ss"));
        this.status = data.getStatus();
        this.total = data.getTotal();
        this.transactionProducts = data.getTransactionProducts().stream().map(TransactionProductResponseDto::new).collect(Collectors.toList());

    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getBuyerUid() {
        return buyerUid;
    }

    public void setBuyerUid(Integer buyerUid) {
        this.buyerUid = buyerUid;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<TransactionProductResponseDto> getTransactionProducts() {
        return transactionProducts;
    }

    public void setTransactionProducts(List<TransactionProductResponseDto> transactionProducts) {
        this.transactionProducts = transactionProducts;
    }
}
