package com.fsse2305.final_project.service.impl;

import com.fsse2305.final_project.data.cartItem.entity.CartItemEntity;
import com.fsse2305.final_project.data.product.entity.ProductEntity;
import com.fsse2305.final_project.data.transaction.TransactionStatus;
import com.fsse2305.final_project.data.transaction.domainObject.TransactionDetailData;
import com.fsse2305.final_project.data.transaction.entity.TransactionEntity;
import com.fsse2305.final_project.data.transactionProduct.entity.TransactionProductEntity;
import com.fsse2305.final_project.data.user.domainObject.FirebaseUserData;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import com.fsse2305.final_project.exception.TransactionGeneralException;
import com.fsse2305.final_project.exception.TransactionNotFoundException;
import com.fsse2305.final_project.repository.TransactionRepository;
import com.fsse2305.final_project.service.CartItemService;
import com.fsse2305.final_project.service.ProductService;
import com.fsse2305.final_project.service.TransactionService;
import com.fsse2305.final_project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
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
        TransactionEntity newTransaction = new TransactionEntity(cartUser);
        List<CartItemEntity> cartItemEntityList =  cartItemService.getCartItems(cartUser);
        for(CartItemEntity cartItemEntity: cartItemEntityList){
            TransactionProductEntity transactionProductEntity = new TransactionProductEntity(newTransaction, cartItemEntity);
            newTransaction.setTotal(newTransaction.getTotal().add(transactionProductEntity.getSubtotal()));
            newTransaction.getTransactionProducts().add(transactionProductEntity);
        }
        transactionRepository.save(newTransaction);

        return new TransactionDetailData(newTransaction);
    }

    @Override
    public TransactionDetailData getTransactionById(FirebaseUserData firebaseUserData, Integer tid){
        try {
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
            return new TransactionDetailData(getTransactionByTidAndUid(tid, userEntity.getUid()));
        } catch (TransactionGeneralException ex){
            logger.warn("Get Transaction Failed: No matching recording found by user");
            throw ex;
        }
    }

    @Override
    public boolean updateTransactionStatusById(FirebaseUserData firebaseUserData, Integer tid){
        UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
        TransactionEntity transactionEntity = getTransactionByTidAndUid(tid, userEntity.getUid());
        for(TransactionProductEntity transactionProductEntity: transactionEntity.getTransactionProducts()){
            ProductEntity productEntity = productService.getProductEntityByPid(transactionProductEntity.getPid());
            productService.setProductStockByEntity(productEntity, transactionProductEntity.getQuantity());
        }
        transactionEntity.setStatus(TransactionStatus.PROCESSING);
        transactionRepository.save(transactionEntity);
        return true;
    }

    public TransactionEntity getTransactionByTidAndUid(Integer tid, Integer uid){
        Optional<TransactionEntity> optionalTransactionEntity = transactionRepository.findByTidAndUid(tid, uid);
        if (optionalTransactionEntity.isEmpty()) {
            logger.warn("Get Transaction Failed: No transaction found");
            throw new TransactionNotFoundException();
        }
        return optionalTransactionEntity.get();
    }
}
