package com.tads4.tads4.dto;

import com.tads4.tads4.entities.Product;

public class ProductDTO {

    private Long ind;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;

    public ProductDTO() {

    }

    public ProductDTO(Long ind, String name, String description, Double price, String imgUrl)
    { this.ind = ind;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDTO(Product entity) {
    ind = entity.getInd();
    name = entity.getName();
    description= entity.getDescription();
    price = entity.getPrice();
    imgUrl = entity.getImgUrl();
    }
    public Long getInd() {
        return ind;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Double getPrice() {
        return price;
    }
    public String getImgUrl() {
        return imgUrl;
    }
}
