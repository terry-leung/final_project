package com.fsse2305.final_project.data.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2305.final_project.data.transaction.TransactionStatus;
import com.fsse2305.final_project.data.transactionProduct.dto.TransactionProductResponseDto;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionResponseDto {
    private Integer tid;
    @JsonProperty("buyer_uid")
    private Integer buyerUid;
    private LocalDateTime datetime;
    private TransactionStatus status;
    private BigDecimal total;
    private List<TransactionProductResponseDto> transactionProductList;
}
