package com.kakao.shopping.dto.cart.request;

import javax.validation.constraints.Min;

public record CartUpdateRequest(
        @Min(1) Long cartId,
        @Min(1) Long quantity
) {
}
