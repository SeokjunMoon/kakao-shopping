package com.kakao.shopping.domain.cart.dto;

import com.kakao.shopping.domain.product.dto.ProductOptionDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record CartItemDTO(
        int id,
        ProductOptionDTO option,
        int quantity,
        int price
) {
}
