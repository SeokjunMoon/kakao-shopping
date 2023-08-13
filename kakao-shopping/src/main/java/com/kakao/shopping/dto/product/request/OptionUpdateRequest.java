package com.kakao.shopping.dto.product.request;

import javax.validation.constraints.Min;

public record OptionUpdateRequest(
        String name,
        @Min(0) Long price
) {
}
