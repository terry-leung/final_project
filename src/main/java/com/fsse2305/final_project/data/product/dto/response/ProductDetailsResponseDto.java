package com.fsse2305.final_project.data.product.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fsse2305.final_project.data.product.domainObject.ProductDetailsData;

import java.math.BigDecimal;

@JsonPropertyOrder({"pid", "name", "description", "image_url", "price", "stock"})
public class ProductDetailsResponseDto {
    private Integer pid;
    private String name;
    private String description;
    @JsonProperty("image_url")
    private String imageUrl;
    private BigDecimal price;
    private Integer stock;

    // TP_detaildata
    public ProductDetailsResponseDto(Integer pid, String name, String description, String imageUrl, BigDecimal price, Integer stock) {
        this.pid = pid;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.stock = stock;
    }

    public ProductDetailsResponseDto(ProductDetailsData data){
        this.pid = data.getPid();
        this.name = data.getName();
        this.description = data.getDescription();
        this.imageUrl = data.getImageUrl();
        this.price = data.getPrice();
        this.stock = data.getStock();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return imageUrl;
    }

    public void setImage_url(String image_url) {
        this.imageUrl = image_url;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
