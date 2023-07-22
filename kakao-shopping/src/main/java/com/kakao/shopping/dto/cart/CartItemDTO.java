package com.kakao.shopping.dto.cart;

import com.kakao.shopping.dto.product.option.OptionDTO;

public record CartItemDTO(
        int id,
        OptionDTO option,
        int quantity,
        int price
) {
}
