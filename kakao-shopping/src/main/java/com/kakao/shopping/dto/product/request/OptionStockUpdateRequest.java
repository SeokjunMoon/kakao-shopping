package com.kakao.shopping.dto.product.request;

public record OptionStockUpdateRequest(
        Long optionId,
        Long stock
) {
}
