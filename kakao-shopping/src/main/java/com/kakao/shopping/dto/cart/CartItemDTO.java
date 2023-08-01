package com.kakao.shopping.dto.cart;

import com.kakao.shopping.dto.product.option.OptionDTO;

public record CartItemDTO(
        Long id,
        CartProductOptionDTO option,
        Long quantity,
        Long price
) {
}
