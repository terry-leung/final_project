package com.fsse2305.final_project.data.cartItem.dto.response;

import com.fsse2305.final_project.data.cartItem.domainObject.CartItemDetailsData;

public class CartModifyingResponseDto {
    private String result;

    public CartModifyingResponseDto(CartItemDetailsData data){
        this.result = (data != null) ? "SUCCESS" : "FAILED" ;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
