package com.kakao.shopping.dto.cart;

public record CartInsertRequest(
        int optionId,
        int quantity
) {
}
