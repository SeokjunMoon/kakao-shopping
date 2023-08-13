package com.kakao.shopping.dto.product.request;

import javax.validation.constraints.Min;

public record ProductUpdateRequest(
        String name,
        String description,
        String image,
        @Min(0) Long price
) {
}
