package com.kakao.shopping.dto.product.request;

import javax.validation.constraints.Min;

public record OptionStockUpdateRequest(
        @Min(1) Long optionId,
        @Min(0) Long stock
) {
}
