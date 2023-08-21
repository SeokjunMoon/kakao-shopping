package com.kakao.shopping.dto.product.request;

import javax.validation.constraints.Min;

public record OptionStockUpdateRequest(
        @Min(value = 1, message = "id는 1 이상의 숫자만 가능합니다.") Long optionId,
        @Min(value = 0, message = "재고 수량은 0 이상의 숫자만 가능합니다.") Long stock
) {
}
