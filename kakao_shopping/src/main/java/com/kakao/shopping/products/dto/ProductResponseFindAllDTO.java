package com.example.kakao_shopping.products.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseFindAllDTO {
    private int id;
    private String productName;
    private String description;
    private int price;

    @Builder
    public ProductResponseFindAllDTO(int id, String productName, String description, int price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }
}
