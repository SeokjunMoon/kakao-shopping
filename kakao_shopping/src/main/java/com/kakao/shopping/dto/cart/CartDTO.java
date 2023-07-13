package com.kakao.shopping.dto.cart;

import java.util.List;

public record CartDTO(
        List<CartProductDTO> products,
        int totalPrice
) {
}
