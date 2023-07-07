package com.kakao.shopping.domain.order.dto;

public record OrderItemDTO(
        String name,
        int quantity,
        int price
) {
}
