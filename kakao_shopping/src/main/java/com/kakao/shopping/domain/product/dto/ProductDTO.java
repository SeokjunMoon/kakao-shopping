package com.kakao.shopping.domain.product.dto;

import java.util.List;

public record ProductDTO(
        int id,
        String productName,
        String description,
        String image,
        int price,
        int starCount,
        List<ProductOptionDTO> options
) {
}
