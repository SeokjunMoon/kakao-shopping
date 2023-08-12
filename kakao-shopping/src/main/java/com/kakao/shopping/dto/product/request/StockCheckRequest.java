package com.kakao.shopping.dto.product.request;

public record StockCheckRequest(
        Long optionId,
        Long quantity
) {
}
