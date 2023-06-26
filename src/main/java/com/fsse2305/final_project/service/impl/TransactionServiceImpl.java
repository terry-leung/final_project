package com.fsse2305.final_project.service.impl;

import com.fsse2305.final_project.data.cartItem.entity.CartItemEntity;
import com.fsse2305.final_project.data.product.entity.ProductEntity;
import com.fsse2305.final_project.data.transaction.TransactionStatus;
import com.fsse2305.final_project.data.transaction.domainObject.TransactionDetailData;
import com.fsse2305.final_project.data.transaction.entity.TransactionEntity;
import com.fsse2305.final_project.data.transactionProduct.entity.TransactionProductEntity;
import com.fsse2305.final_project.data.user.domainObject.FirebaseUserData;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import com.fsse2305.final_project.exception.CartItemNotFoundException;
import com.fsse2305.final_project.exception.TransactionGeneralException;
import com.fsse2305.final_project.exception.TransactionNotFoundException;
import com.fsse2305.final_project.exception.TransactionProcessingException;
import com.fsse2305.final_project.repository.TransactionRepository;
import com.fsse2305.final_project.service.CartItemService;
import com.fsse2305.final_project.service.ProductService;
import com.fsse2305.final_project.service.TransactionService;
import com.fsse2305.final_project.service.UserService;
import jakarta.transaction.Transactional;
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
        try {
            UserEntity cartUser = userService.getEntityByFirebaseUserData(firebaseUserData);
            List<CartItemEntity> cartItemEntityList = cartItemService.getCartItems(cartUser);
            if (cartItemEntityList.isEmpty()) {
                logger.warn("Create Transaction Failed: No cartItems found.");
                throw new CartItemNotFoundException();
            }
            TransactionEntity newTransaction = new TransactionEntity(cartUser);
            for (CartItemEntity cartItemEntity : cartItemEntityList) {
                //Use TransactionProduct Repository in TP_Service for below
                TransactionProductEntity transactionProductEntity = new TransactionProductEntity(newTransaction, cartItemEntity);
                newTransaction.setTotal(newTransaction.getTotal().add(transactionProductEntity.getSubtotal()));
                newTransaction.getTransactionProducts().add(transactionProductEntity);
            }
            transactionRepository.save(newTransaction);

            return new TransactionDetailData(newTransaction);
        } catch (TransactionGeneralException ex){
            logger.warn("Create Transaction Failed");
            throw ex;
        }
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
    @Transactional
    public boolean updateTransactionStatusById(FirebaseUserData firebaseUserData, Integer tid){
        try {
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
            TransactionEntity transactionEntity = getTransactionByTidAndUid(tid, userEntity.getUid());
            if (!(transactionEntity.getStatus() == TransactionStatus.PREPARE)) {
                logger.warn("Transaction Processing Failed: Transaction has already processed");
                throw new TransactionProcessingException();
            }
            for (TransactionProductEntity transactionProductEntity : transactionEntity.getTransactionProducts()) {
                ProductEntity productEntity = productService.getProductEntityByPid(transactionProductEntity.getPid());
                productService.setProductStockByEntity(productEntity, transactionProductEntity.getQuantity());
            }
            transactionEntity.setStatus(TransactionStatus.PROCESSING);
            transactionRepository.save(transactionEntity);
            return true;
        } catch (TransactionGeneralException ex){
            logger.warn("Transaction Processing Failed");
            throw ex;
        }
    }

    @Override
    @Transactional
    public TransactionDetailData updateTransactionSuccessById(FirebaseUserData firebaseUserData, Integer tid){
        try {
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
            TransactionEntity transactionEntity = getTransactionByTidAndUid(tid, userEntity.getUid());
            if (!(transactionEntity.getStatus() == TransactionStatus.PROCESSING)) {
                logger.warn("Transaction Processing Failed: Transaction has not yet been processed");
                throw new TransactionProcessingException();
            }
            cartItemService.deleteCartItemsByUid(userEntity.getUid());
            transactionEntity.setStatus(TransactionStatus.SUCCESS);
            transactionEntity = transactionRepository.save(transactionEntity);
            return new TransactionDetailData(transactionEntity);
        } catch (TransactionGeneralException ex){
            logger.warn("Transaction Processing Failed");
            throw ex;
        }
    }

    public TransactionEntity getTransactionByTidAndUid(Integer tid, Integer uid){
        // return .... .orElseThrow(exception::new);
        Optional<TransactionEntity> optionalTransactionEntity = transactionRepository.findByTidAndUid(tid, uid);
        if (optionalTransactionEntity.isEmpty()) {
            logger.warn("Get Transaction Failed: No transaction found");
            throw new TransactionNotFoundException();
        }
        return optionalTransactionEntity.get();
    }
}
