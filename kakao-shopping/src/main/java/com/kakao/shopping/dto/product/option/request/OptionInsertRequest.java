package com.kakao.shopping.dto.product.option.request;

import com.kakao.shopping.domain.Product;

public record OptionInsertRequest(
        Product product,
        String name,
        Long price
) {
}
