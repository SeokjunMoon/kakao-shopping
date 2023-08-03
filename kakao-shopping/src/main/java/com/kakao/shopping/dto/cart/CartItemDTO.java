package com.kakao.shopping.dto.cart;

public record CartItemDTO(
        Long id,
        CartProductOptionDTO option,
        Long quantity,
        Long price
) {
}
