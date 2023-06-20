package com.fsse2305.final_project.service.impl;

import com.fsse2305.final_project.data.cartItem.domainObject.CartItemDetailsData;
import com.fsse2305.final_project.data.cartItem.entity.CartItemEntity;
import com.fsse2305.final_project.data.user.domainObject.FirebaseUserData;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import com.fsse2305.final_project.repository.CartItemRepository;
import com.fsse2305.final_project.service.CartItemService;
import com.fsse2305.final_project.service.ProductService;
import com.fsse2305.final_project.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {
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
//        UserEntity loginUser = userService.getEntityByFirebaseUserData(firebaseUserData);
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setUser(userService.getEntityByFirebaseUserData(firebaseUserData));
        cartItemEntity.setProduct(productService.getProductEntityByPid(pid));
        cartItemEntity.setQuantity(quantity);
        cartItemEntity = cartItemRepository.save(cartItemEntity);
        return new CartItemDetailsData(cartItemEntity);
    }

    @Override
    public List<CartItemDetailsData> getUserCartItems(FirebaseUserData firebaseUserData){
        UserEntity cartUser = userService.getEntityByFirebaseUserData(firebaseUserData);
        List<CartItemDetailsData> cartItemDetailsDataList = new ArrayList<>();
        for (CartItemEntity cartItemEntity : cartItemRepository.getAllCartItemsByUid(cartUser.getUid())){
            CartItemDetailsData cartItemDetailsData = new CartItemDetailsData(cartItemEntity);
            cartItemDetailsDataList.add(cartItemDetailsData);
        }
        return cartItemDetailsDataList;
    }

    @Override
    public CartItemDetailsData updateCartQuantity(FirebaseUserData firebaseUserData, Integer pid, Integer quantity){
        UserEntity cartUser = userService.getEntityByFirebaseUserData(firebaseUserData);
        CartItemEntity updateCartItem = cartItemRepository.findCartItemsByUidAndPid(cartUser.getUid(), pid);
        updateCartItem.setQuantity(quantity);
        updateCartItem = cartItemRepository.save(updateCartItem);
        return new CartItemDetailsData(updateCartItem);
    }

    @Override
    @Transactional
    public CartItemDetailsData deleteCartItem(FirebaseUserData firebaseUserData, Integer pid){
        UserEntity cartUser = userService.getEntityByFirebaseUserData(firebaseUserData);
        CartItemEntity cartItemEntity = cartItemRepository.findCartItemsByUidAndPid(cartUser.getUid(), pid);
        cartItemRepository.delete(cartItemEntity);
        return new CartItemDetailsData(cartItemEntity);
    }


}
