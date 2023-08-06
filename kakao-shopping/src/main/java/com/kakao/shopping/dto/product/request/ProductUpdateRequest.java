package com.kakao.shopping.dto.product.request;

public record ProductUpdateRequest(
        String name,
        String description,
        String image,
        Long price
) {
}
