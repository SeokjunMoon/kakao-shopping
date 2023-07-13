package com.kakao.shopping.dto.cart;

public record CartUpdateRequest(
        int cartId,
        int quantity
) {
}
