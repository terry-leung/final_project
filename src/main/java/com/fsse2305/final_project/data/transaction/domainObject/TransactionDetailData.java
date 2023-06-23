package com.fsse2305.final_project.data.transaction.domainObject;

import com.fsse2305.final_project.data.transaction.TransactionStatus;
import com.fsse2305.final_project.data.transaction.entity.TransactionEntity;
import com.fsse2305.final_project.data.transactionProduct.domainObject.TransactionProductDetailData;
import com.fsse2305.final_project.data.user.domainObject.UserDetailData;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionDetailData {
    private Integer tid;
    private UserDetailData user;
    private LocalDateTime datetime;
    private TransactionStatus status;
    private BigDecimal total;
    private List<TransactionProductDetailData> transactionProducts;

    public TransactionDetailData(TransactionEntity entity){
        this.tid = entity.getTid();
        this.user = new UserDetailData(entity.getUser());
        this.datetime = entity.getDatetime();
        this.status = entity.getStatus();
        this.total = entity.getTotal();
        // = use for loop in setter
        this.transactionProducts = entity.getTransactionProducts().stream().map(TransactionProductDetailData::new).collect(Collectors.toList());
    }

    public List<TransactionProductDetailData> getTransactionProducts() {
        return transactionProducts;
    }

    public void setTransactionProducts(List<TransactionProductDetailData> transactionProducts) {
        this.transactionProducts = transactionProducts;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public UserDetailData getUser() {
        return user;
    }

    public void setUser(UserDetailData user) {
        this.user = user;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
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
}
