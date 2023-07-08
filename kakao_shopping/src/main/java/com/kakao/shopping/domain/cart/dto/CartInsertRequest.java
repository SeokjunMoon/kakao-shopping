package com.kakao.shopping.domain.cart.dto;

public record CartInsertRequest(
        int optionId,
        int quantity
) {
}
