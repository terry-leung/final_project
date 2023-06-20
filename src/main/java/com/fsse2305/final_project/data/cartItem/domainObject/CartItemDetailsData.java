package com.fsse2305.final_project.data.cartItem.domainObject;

import com.fsse2305.final_project.data.cartItem.entity.CartItemEntity;
import com.fsse2305.final_project.data.product.domainObject.ProductDetailsData;
import com.fsse2305.final_project.data.user.entity.UserEntity;

public class CartItemDetailsData {
    private Integer cid;
    private ProductDetailsData product;
    private Integer quantity;

    public CartItemDetailsData(CartItemEntity entity){
        this.cid = entity.getCid();
        this.product = new ProductDetailsData(entity.getProduct());
        this.quantity = entity.getQuantity();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public ProductDetailsData getProduct() {
        return product;
    }

    public void setProduct(ProductDetailsData product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
