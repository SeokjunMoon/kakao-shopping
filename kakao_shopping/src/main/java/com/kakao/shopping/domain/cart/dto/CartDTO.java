package com.kakao.shopping.domain.cart.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CartDTO {
    final private List<CartProductDTO> products;
    final private int totalPrice;

    @Builder
    public CartDTO(List<CartProductDTO> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
