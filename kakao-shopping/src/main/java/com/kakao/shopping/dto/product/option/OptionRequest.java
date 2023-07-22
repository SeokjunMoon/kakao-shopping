package com.kakao.shopping.dto.product.option;

import com.kakao.shopping.domain.Product;

public class OptionRequest {
    public static record Insert(
            Product product,
            String name,
            Long price
    ) {}
}
