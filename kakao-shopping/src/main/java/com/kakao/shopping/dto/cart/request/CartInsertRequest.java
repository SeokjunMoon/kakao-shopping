package com.kakao.shopping.dto.cart.request;

import javax.validation.constraints.Min;

public record CartInsertRequest(
        @Min(1) Long optionId,
        @Min(1) Long quantity
) {
}
