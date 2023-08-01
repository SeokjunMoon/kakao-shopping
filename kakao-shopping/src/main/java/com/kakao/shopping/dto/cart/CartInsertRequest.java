package com.kakao.shopping.dto.cart;

public record CartInsertRequest(
        Long optionId,
        Long quantity
) {
}
