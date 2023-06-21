package com.fsse2305.final_project.service.impl;

import com.fsse2305.final_project.data.cartItem.entity.CartItemEntity;
import com.fsse2305.final_project.data.transaction.domainObject.TransactionDetailData;
import com.fsse2305.final_project.data.user.domainObject.FirebaseUserData;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import com.fsse2305.final_project.repository.TransactionRepository;
import com.fsse2305.final_project.service.CartItemService;
import com.fsse2305.final_project.service.ProductService;
import com.fsse2305.final_project.service.TransactionService;
import com.fsse2305.final_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final ProductService productService;
    private final CartItemService cartItemService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserService userService, ProductService productService, CartItemService cartItemService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    @Override
    public TransactionDetailData createTransaction(FirebaseUserData firebaseUserData){
        UserEntity cartUser = userService.getEntityByFirebaseUserData(firebaseUserData);
        List<CartItemEntity> cartItemEntityList =  cartItemService.getCartItems(cartUser);
        return null;
    }
}
