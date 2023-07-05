package com.kakao.shopping.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductListItemDTO {
    final private int id;
    final private String productName;
    final private String description;
    final private String image;
    final private int price;

    @Builder
    public ProductListItemDTO(int id, String productName, String description, String image, int price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
