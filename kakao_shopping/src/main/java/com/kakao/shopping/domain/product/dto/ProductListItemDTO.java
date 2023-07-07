package com.kakao.shopping.domain.product.dto;

public record ProductListItemDTO(
        int id,
        String productName,
        String description,
        String image,
        int price
) {
}
