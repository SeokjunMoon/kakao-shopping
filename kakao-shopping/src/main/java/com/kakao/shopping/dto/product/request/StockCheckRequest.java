package com.kakao.shopping.dto.product.request;

import javax.validation.constraints.Min;

public record StockCheckRequest(
        @Min(1) Long optionId,
        @Min(1) Long quantity
) {
}
