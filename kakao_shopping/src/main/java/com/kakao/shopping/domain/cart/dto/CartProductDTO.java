package com.kakao.shopping.domain.cart.dto;

import java.util.List;

public record CartProductDTO(
        int id,
        String productName,
        List<CartItemDTO> carts
) {
}
