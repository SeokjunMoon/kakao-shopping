package com.kakao.shopping.dto.order;

public record OrderItemDTO(
        String name,
        int quantity,
        int price
) {
}
