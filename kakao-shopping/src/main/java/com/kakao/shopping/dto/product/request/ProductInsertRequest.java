package com.kakao.shopping.dto.product.request;

import javax.validation.constraints.Min;

public record ProductInsertRequest(
        String name,
        String description,
        String image,
        @Min(0) Long price
) {
}
