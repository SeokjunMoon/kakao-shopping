package com.kakao.shopping.dto.cart;

public record CartUpdateRequest(
        Long cartId,
        Long quantity
) {
}
