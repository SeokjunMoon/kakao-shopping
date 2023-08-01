package com.kakao.shopping.dto.cart;

public record UpdatedCartDTO(
        Long id,
        Long productOptionId,
        String productOptionName,
        Long quantity,
        Long price
) {
}
