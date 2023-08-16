package com.kakao.shopping.dto.product.request;

import javax.validation.constraints.Min;

public record ProductUpdateRequest(
        String name,
        String description,
        String image,
        @Min(value = 0, message = "가격은 0 이상의 숫자만 가능합니다.") Long price
) {
}
