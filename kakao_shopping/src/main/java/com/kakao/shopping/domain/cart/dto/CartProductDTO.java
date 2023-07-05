package com.kakao.shopping.domain.cart.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartProductDTO {
    final private int id;
    final private String productName;
    final private List<CartItemDTO> carts;

    @Builder
    public CartProductDTO(int id, String productName, List<CartItemDTO> carts) {
        this.id = id;
        this.productName = productName;
        this.carts = carts;
    }
}
