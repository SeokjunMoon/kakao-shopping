package com.kakao.shopping.dto.cart;

import java.util.List;

public record CartProductDTO(
        Long id,
        String name,
        List<CartItemDTO> carts
) {
}
