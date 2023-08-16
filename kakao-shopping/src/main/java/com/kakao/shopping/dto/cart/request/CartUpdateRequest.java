package com.kakao.shopping.dto.cart.request;

import javax.validation.constraints.Min;

public record CartUpdateRequest(
        @Min(value = 1, message = "id는 1 이상의 숫자만 가능합니다.") Long cartId,
        @Min(value = 1, message = "수량은 1 이상의 숫자만 가능합니다.") Long quantity
) {
}
