package com.kakao.shopping.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductDTO {
    final private int id;
    final private String productName;
    final private String description;
    final private String image;
    final private int price;
    final private int starCount;
    final private List<ProductOptionDTO> options;

    @Builder
    public ProductDTO(int id, String productName, String description, String image, int price, int starCount, List<ProductOptionDTO> options) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.starCount = starCount;
        this.options = options;
    }
}
