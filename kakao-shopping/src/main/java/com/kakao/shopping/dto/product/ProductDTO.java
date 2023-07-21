package com.kakao.shopping.dto.product;

import com.kakao.shopping.domain.Option;

import java.util.List;

public record ProductDTO(
        Long id,
        String name,
        String description,
        String image,
        Long price,
        Long starCount,
        List<Option> options
) {
}
