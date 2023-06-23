package com.fsse2305.final_project.service.impl;

import com.fsse2305.final_project.data.cartItem.domainObject.CartItemDetailsData;
import com.fsse2305.final_project.data.cartItem.entity.CartItemEntity;
import com.fsse2305.final_project.data.product.entity.ProductEntity;
import com.fsse2305.final_project.data.user.domainObject.FirebaseUserData;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import com.fsse2305.final_project.exception.CartItemModifyingException;
import com.fsse2305.final_project.exception.ExceedMaximumStockException;
import com.fsse2305.final_project.exception.ProductNotFoundException;
import com.fsse2305.final_project.repository.CartItemRepository;
import com.fsse2305.final_project.service.CartItemService;
import com.fsse2305.final_project.service.ProductService;
import com.fsse2305.final_project.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserService userService, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public CartItemDetailsData putCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity){
        try {
            UserEntity cartUser = userService.getEntityByFirebaseUserData(firebaseUserData);

            Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findCartItemsByUidAndPid(cartUser.getUid(), pid);
            ProductEntity productEntity = productService.getProductEntityByPid(pid);

            if (optionalCartItemEntity.isEmpty()) {
                if (quantity >= productService.getProductById(pid).getStock()) {
                    logger.warn("Add item error: Exceed maximum stock");
                    throw new ExceedMaximumStockException();
                } else {
                    CartItemEntity newCartItemEntity = new CartItemEntity(productEntity, cartUser, quantity);
                    cartItemRepository.save(newCartItemEntity);
                    return new CartItemDetailsData(newCartItemEntity);
                }
            } else {
                CartItemEntity cartItemEntity = optionalCartItemEntity.get();
                if (quantity + cartItemEntity.getQuantity() >= productService.getProductById(pid).getStock()) {
                    logger.warn("Add item error: Exceed maximum stock");
                    throw new ExceedMaximumStockException();
                } else {
                    cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
                    cartItemRepository.save(cartItemEntity);
                    return new CartItemDetailsData(cartItemEntity);
                }
            }
        } catch (CartItemModifyingException ex){
            logger.warn("Put CartItem failed: Product not found");
            throw ex;
        }

    }

    @Override
    public List<CartItemDetailsData> getUserCartItems(FirebaseUserData firebaseUserData){
        // can create method to get userentity
        UserEntity cartUser = userService.getEntityByFirebaseUserData(firebaseUserData);
        List<CartItemDetailsData> cartItemDetailsDataList = new ArrayList<>();
        for (CartItemEntity cartItemEntity : cartItemRepository.getAllCartItemsByUid(cartUser.getUid())){
            CartItemDetailsData cartItemDetailsData = new CartItemDetailsData(cartItemEntity);
            cartItemDetailsDataList.add(cartItemDetailsData);
        }
        return cartItemDetailsDataList;
    }

    @Override

    public CartItemDetailsData updateCartQuantity(FirebaseUserData firebaseUserData, Integer pid, Integer quantity) {
        UserEntity cartUser = userService.getEntityByFirebaseUserData(firebaseUserData);
        Optional<CartItemEntity> optionalUpdateCartItem = cartItemRepository.findCartItemsByUidAndPid(cartUser.getUid(), pid);

        if (optionalUpdateCartItem.isEmpty()) {
            logger.warn("Update CartItem failed: Product not found");
            throw new ProductNotFoundException();
        }

        CartItemEntity cartItemEntity = optionalUpdateCartItem.get();

        if (quantity < 0) {
            logger.warn("Update CartItem failed: Invalid quantity");
            throw new CartItemModifyingException();
        }
        if (quantity > productService.getProductById(pid).getStock()) {
            logger.warn("Add item error: Exceed maximum stock");
            throw new ExceedMaximumStockException();
        }
        if (quantity == 0) {
            cartItemRepository.deleteById(cartItemEntity.getCid());
            cartItemEntity.setQuantity(quantity);
        }
        cartItemEntity.setQuantity(quantity);
        cartItemEntity = cartItemRepository.save(cartItemEntity);
        return new CartItemDetailsData(cartItemEntity);
    }

    @Override
    @Transactional
    public boolean deleteCartItem(FirebaseUserData firebaseUserData, Integer pid){
        UserEntity cartUser = userService.getEntityByFirebaseUserData(firebaseUserData);
//        Optional<CartItemEntity> optionCartItemEntity = cartItemRepository.findCartItemsByUidAndPid(cartUser.getUid(), pid);
//        if (optionCartItemEntity.isEmpty()){
//            logger.warn("Update CartItem failed: Product not found");
//            throw new ProductNotFoundException();
//        } else {
//            CartItemEntity cartItemEntity = optionCartItemEntity.get();
//            cartItemRepository.delete(cartItemEntity);
//            return new CartItemDetailsData(cartItemEntity);
//        }

        if (cartItemRepository.deleteByUidAndPid(cartUser.getUid(), pid) <= 0){
            logger.warn("Delete item error: No matched cartItem found");
            throw new CartItemModifyingException();
        }
        return true;
    }

    @Override
    public List<CartItemEntity> getCartItems(UserEntity cartUser){
        return cartItemRepository.getAllCartItemsByUid(cartUser.getUid());
    }
//    need to throw ex in main method
//    public void getEntityByUidAndPid(Integer uid , Integer pid){
//        Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findCartItemsByUidAndPid(uid, pid);
//
//    }

    @Override
    public void deleteCartItemsByUid(Integer uid){
        cartItemRepository.deleteByUid(uid);
    }

}
