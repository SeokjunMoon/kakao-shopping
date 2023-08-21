package com.kakao.shopping.dto.product.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record OptionUpdateRequest(
        @NotNull(message = "옵션명을 입력해주세요.")
        String name,
        @Min(value = 0, message = "가격은 0 이상의 숫자만 가능합니다.") Long price
) {
}
