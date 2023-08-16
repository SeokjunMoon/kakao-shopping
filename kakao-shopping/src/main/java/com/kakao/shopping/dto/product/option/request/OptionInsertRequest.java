package com.kakao.shopping.dto.product.option.request;

import com.kakao.shopping.domain.Product;

import javax.validation.constraints.Min;

public record OptionInsertRequest(
        Product product,
        String name,
        @Min(value = 0, message = "가격은 0 이상의 숫자만 가능합니다.") Long price
) {
}
