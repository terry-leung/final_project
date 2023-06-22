package com.fsse2305.final_project.resultStatus;

import com.fsse2305.final_project.data.cartItem.domainObject.CartItemDetailsData;

public class ResultResponseDto {
    private String result;

    public ResultResponseDto(boolean isSuccess){
        this.result = isSuccess ? "SUCCESS" : "FAILED" ;
    }
    public ResultResponseDto(CartItemDetailsData data){
        this.result = (data != null) ? "SUCCESS" : "FAILED" ;
    }

    public String getResult() {
        return result;
    }

    private void setResult(String result) {
        this.result = result;
    }
}
