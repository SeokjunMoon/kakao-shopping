package com.kakao.shopping.dto.cart.request;

public record CartInsertRequest(
        Long optionId,
        Long quantity
) {
}
