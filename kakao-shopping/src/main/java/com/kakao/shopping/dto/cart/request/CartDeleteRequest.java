package com.kakao.shopping.dto.cart.request;

import javax.validation.constraints.Min;

public record CartDeleteRequest(
        @Min(value = 1, message = "id는 1 이상의 숫자만 가능합니다.") Long cartId
) {
}
