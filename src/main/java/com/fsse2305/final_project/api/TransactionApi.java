package com.fsse2305.final_project.api;

import com.fsse2305.final_project.data.transaction.domainObject.TransactionDetailData;
import com.fsse2305.final_project.data.transaction.dto.response.TransactionResponseDto;
import com.fsse2305.final_project.data.user.domainObject.FirebaseUserData;
import com.fsse2305.final_project.resultStatus.ResultResponseDto;
import com.fsse2305.final_project.service.TransactionService;
import com.fsse2305.final_project.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionApi {
    private final TransactionService transactionService;

    @Autowired
    public TransactionApi(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/prepare")
    public TransactionResponseDto createTransaction(JwtAuthenticationToken jwtToken){
        return new TransactionResponseDto(transactionService.createTransaction(JwtUtil.getFirebaseUserData(jwtToken)));
    }

    @GetMapping("/{tid}")
    public TransactionResponseDto getTransactionDetailById(JwtAuthenticationToken jwtToken, @PathVariable Integer tid){

        return new TransactionResponseDto(transactionService.getTransactionById(JwtUtil.getFirebaseUserData(jwtToken), tid));
    }

    @PatchMapping("/{tid}/pay")
    public ResultResponseDto updateTransactionStatusById(JwtAuthenticationToken jwtToken, @PathVariable Integer tid){

        return new ResultResponseDto(transactionService.updateTransactionStatusById(JwtUtil.getFirebaseUserData(jwtToken), tid));
    }
}
