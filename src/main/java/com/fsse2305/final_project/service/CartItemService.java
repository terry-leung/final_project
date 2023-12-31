package com.fsse2305.final_project.service;

import com.fsse2305.final_project.data.cartItem.domainObject.CartItemDetailsData;
import com.fsse2305.final_project.data.cartItem.entity.CartItemEntity;
import com.fsse2305.final_project.data.user.domainObject.FirebaseUserData;
import com.fsse2305.final_project.data.user.entity.UserEntity;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CartItemService {
    CartItemDetailsData putCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity);

    List<CartItemDetailsData> getUserCartItems(FirebaseUserData firebaseUserData);

    CartItemDetailsData updateCartQuantity(FirebaseUserData firebaseUserData, Integer pid, Integer quantity);

    @Transactional
    boolean deleteCartItem(FirebaseUserData firebaseUserData, Integer pid);

    List<CartItemEntity> getCartItems(UserEntity cartUser);

    void deleteCartItemsByUid(Integer uid);
}
