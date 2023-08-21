package com.kakao.shopping.dto.product.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record ProductUpdateRequest(
        @NotNull(message = "상품명을 입력해주세요.")
        String name,
        String description,
        String image,
        @Min(value = 0, message = "가격은 0 이상의 숫자만 가능합니다.") Long price
) {
}
