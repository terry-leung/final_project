package com.fsse2305.final_project.data.transaction.domainObject;

import com.fsse2305.final_project.data.transaction.TransactionStatus;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDetailData {
    private Integer tid;
    private UserEntity user;
    private LocalDateTime datetime;
    private TransactionStatus status;
    private BigDecimal total;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
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
