package com.kakao.shopping.products.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductOptionDTO {
    final private int id;
    final private String name;
    final private int price;

    @Builder
    public ProductOptionDTO(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
