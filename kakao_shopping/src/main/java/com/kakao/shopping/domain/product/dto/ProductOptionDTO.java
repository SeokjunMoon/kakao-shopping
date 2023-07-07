package com.kakao.shopping.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public record ProductOptionDTO(
        int id,
        String optionName,
        int price
) {
}
