package com.kakao.shopping.dto.product;

import com.kakao.shopping.domain.ProductOption;

import java.util.List;

public record ProductDTO(
        Long id,
        String name,
        String description,
        String image,
        Long price,
        Long starCount,
        List<ProductOption> productOptions
) {
}
