package com.fsse2305.final_project.service;

import com.fsse2305.final_project.data.transaction.domainObject.TransactionDetailData;
import com.fsse2305.final_project.data.user.domainObject.FirebaseUserData;

public interface TransactionService {
    TransactionDetailData createTransaction(FirebaseUserData firebaseUserData);

    TransactionDetailData getTransactionById(FirebaseUserData firebaseUserData, Integer tid);
}
