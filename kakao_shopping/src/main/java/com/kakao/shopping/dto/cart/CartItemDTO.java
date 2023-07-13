package com.kakao.shopping.dto.cart;

import com.kakao.shopping.dto.product.ProductOptionDTO;

public record CartItemDTO(
        int id,
        ProductOptionDTO option,
        int quantity,
        int price
) {
}
