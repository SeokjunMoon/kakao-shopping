package com.kakao.shopping.products.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseFindAllDTO {
    final private int id;
    final private String name;
    final private String description;
    final private String image;
    final private int price;

    @Builder
    public ProductResponseFindAllDTO(int id, String name, String description, String image, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
