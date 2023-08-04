package com.kakao.shopping.dto.order;

public record OrderItemDTO(
        String name,
        Long quantity,
        Long price
) {
}
