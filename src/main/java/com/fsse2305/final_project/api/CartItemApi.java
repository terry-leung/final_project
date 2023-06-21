package com.fsse2305.final_project.api;

import com.fsse2305.final_project.data.cartItem.domainObject.CartItemDetailsData;
import com.fsse2305.final_project.data.cartItem.dto.response.CartItemResponseDto;
import com.fsse2305.final_project.data.cartItem.dto.response.CartModifyingResponseDto;
import com.fsse2305.final_project.data.user.domainObject.FirebaseUserData;
import com.fsse2305.final_project.service.CartItemService;
import com.fsse2305.final_project.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartItemApi {
    private final CartItemService cartItemService;

    @Autowired
    public CartItemApi(CartItemService cartItemService){
        this.cartItemService = cartItemService;

    }

    @PutMapping("/{pid}/{quantity}")
    public CartModifyingResponseDto putCartItem(JwtAuthenticationToken jwtToken, @PathVariable Integer pid, @PathVariable Integer quantity){
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwtToken);
        CartItemDetailsData cartItemDetailsData = cartItemService.putCartItem(firebaseUserData, pid, quantity);

        return new CartModifyingResponseDto(cartItemDetailsData);
    }

    @GetMapping()
    public List<CartItemResponseDto> getUserCart(JwtAuthenticationToken jwtToken){
        List<CartItemResponseDto> cartItemResponseDtoList = new ArrayList<>();

        for (CartItemDetailsData cartItemDetailsData : cartItemService.getUserCartItems(JwtUtil.getFirebaseUserData(jwtToken))){
            cartItemResponseDtoList.add(new CartItemResponseDto(cartItemDetailsData));
        }
        return cartItemResponseDtoList;
    }

    @PatchMapping("/{pid}/{quantity}")
    public CartItemResponseDto updateCartQuantity(
            JwtAuthenticationToken jwtAToken, @PathVariable Integer pid, @PathVariable Integer quantity){
        FirebaseUserData firebaseUserData = JwtUtil.getFirebaseUserData(jwtAToken);
        CartItemDetailsData cartItemDetailsData = cartItemService.updateCartQuantity(firebaseUserData, pid, quantity);
        return new CartItemResponseDto(cartItemDetailsData);
    }

    @DeleteMapping("/{pid}")
    public CartModifyingResponseDto deleteCartItem(JwtAuthenticationToken jwtToken, @PathVariable Integer pid){
        boolean ifSuccess = cartItemService.deleteCartItem(JwtUtil.getFirebaseUserData(jwtToken), pid);

        return new CartModifyingResponseDto(ifSuccess);
    }
}


