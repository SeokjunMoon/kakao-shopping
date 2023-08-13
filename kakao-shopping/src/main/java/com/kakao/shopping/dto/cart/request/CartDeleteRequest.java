package com.kakao.shopping.dto.cart.request;

import javax.validation.constraints.Min;

public record CartDeleteRequest(
        @Min(1) Long cartId
) {
}
