package com.kakao.shopping.dto.product.option.request;

import com.kakao.shopping.domain.Product;

import javax.validation.constraints.Min;

public record OptionInsertRequest(
        Product product,
        String name,
        @Min(0) Long price
) {
}
