package com.kakao.shopping.dto;

import java.util.List;

public record CartDTO(
        List<CartProductDTO> products,
        int totalPrice
) {
}
