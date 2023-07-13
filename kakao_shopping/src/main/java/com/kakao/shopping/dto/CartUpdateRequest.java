package com.kakao.shopping.dto;

public record CartUpdateRequest(
        int cartId,
        int quantity
) {
}
