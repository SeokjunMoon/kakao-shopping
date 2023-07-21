package com.kakao.shopping.dto.product;

public record ProductListItemDTO(
        Long id,
        String productName,
        String description,
        String image,
        Long price,
        Long starCount
) {
}
