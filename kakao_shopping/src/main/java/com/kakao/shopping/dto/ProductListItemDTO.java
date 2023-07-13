package com.kakao.shopping.dto;

public record ProductListItemDTO(
        int id,
        String productName,
        String description,
        String image,
        int price
) {
}
