package com.fsse2305.final_project.data.transaction.entity;

import com.fsse2305.final_project.data.cartItem.entity.CartItemEntity;
import com.fsse2305.final_project.data.transaction.TransactionStatus;
import com.fsse2305.final_project.data.transactionProduct.entity.TransactionProductEntity;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tid;

    @ManyToOne
    @JoinColumn(name = "buyerUid", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<TransactionProductEntity> transactionProducts;

    public TransactionEntity(UserEntity cartUser){
        this.user = cartUser;
        this.datetime = LocalDateTime.now();
        this.status = TransactionStatus.PREPARE;
        this.total = BigDecimal.ZERO;
        this.transactionProducts = new ArrayList<>();
    }

    public List<TransactionProductEntity> getTransactionProducts() {
        return transactionProducts;
    }

    public void setTransactionProducts(List<TransactionProductEntity> transactionProducts) {
        this.transactionProducts = transactionProducts;
    }

    public TransactionEntity(){

    }

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
