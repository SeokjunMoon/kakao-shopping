package com.kakao.shopping.domain.cart.dto;

import java.util.List;

public record CartDTO(
        List<CartProductDTO> products,
        int totalPrice
) {
}
