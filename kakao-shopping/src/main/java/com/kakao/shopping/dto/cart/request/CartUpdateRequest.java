package com.kakao.shopping.dto.cart.request;

public record CartUpdateRequest(
        Long cartId,
        Long quantity
) {
}
