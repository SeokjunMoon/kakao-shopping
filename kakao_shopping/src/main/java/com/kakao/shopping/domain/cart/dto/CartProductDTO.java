package com.kakao.shopping.domain.cart.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CartProductDTO {
    final private int id;
    final private String name;
    final private List<CartItemDTO> cartItems;

    @Builder
    public CartProductDTO(int id, String name, List<CartItemDTO> cartItems) {
        this.id = id;
        this.name = name;
        this.cartItems = cartItems;
    }
}
