package com.kakao.shopping.dto;

public record OrderItemDTO(
        String name,
        int quantity,
        int price
) {
}
