package com.fsse2305.final_project.data.cartItem.dto.response;

import com.fsse2305.final_project.data.cartItem.domainObject.CartItemDetailsData;
import com.fsse2305.final_project.exception.CartItemModifyingException;

public class CartModifyingResponseDto {
    private String result;

    public CartModifyingResponseDto(boolean isSuccess){
        this.result = isSuccess ? "SUCCESS" : "FAILED" ;
    }
    public CartModifyingResponseDto(CartItemDetailsData data){
        this.result = (data != null) ? "SUCCESS" : "FAILED" ;
    }

    public String getResult() {
        return result;
    }

    private void setResult(String result) {
        this.result = result;
    }
}
