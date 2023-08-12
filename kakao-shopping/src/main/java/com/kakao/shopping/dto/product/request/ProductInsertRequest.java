package com.kakao.shopping.dto.product.request;

public record ProductInsertRequest(
        String name,
        String description,
        String image,
        Long price
) {
}
