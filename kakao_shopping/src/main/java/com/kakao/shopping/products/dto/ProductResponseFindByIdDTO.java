package com.kakao.shopping.products.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class ProductResponseFindByIdDTO {
    final private int id;
    final private String name;
    final private String description;
    final private String image;
    final private int price;
    final private int starCount;
    final private List<ProductOptionDTO> options;

    @Builder
    public ProductResponseFindByIdDTO(int id, String name, String description, String image, int price, int starCount, List<ProductOptionDTO> options) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.starCount = starCount;
        this.options = options;
    }
}
