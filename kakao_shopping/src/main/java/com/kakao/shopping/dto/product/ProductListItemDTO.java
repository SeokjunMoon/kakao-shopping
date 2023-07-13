package com.kakao.shopping.dto.product;

public record ProductListItemDTO(
        int id,
        String productName,
        String description,
        String image,
        int price
) {
}
