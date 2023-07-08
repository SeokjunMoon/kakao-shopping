package com.kakao.shopping.domain.cart.dto;

public record CartUpdateRequest(
        int cartId,
        int quantity
) {
}
