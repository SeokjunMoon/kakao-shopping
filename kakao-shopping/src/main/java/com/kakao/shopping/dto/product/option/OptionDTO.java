package com.kakao.shopping.dto.product.option;

import com.kakao.shopping.dto.product.ProductListItemDTO;

public record OptionDTO(
        ProductListItemDTO product,
        String name,
        Long price
) {
}
