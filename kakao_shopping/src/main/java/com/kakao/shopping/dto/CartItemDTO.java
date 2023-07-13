package com.kakao.shopping.dto;

public record CartItemDTO(
        int id,
        ProductOptionDTO option,
        int quantity,
        int price
) {
}
