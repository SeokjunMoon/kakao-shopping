package com.kakao.shopping._core.utils;

import com.kakao.shopping.domain.Cart;

import java.util.List;

public class CartPriceCalculator {
    private final List<Cart> carts;

    public CartPriceCalculator(List<Cart> carts) {
        this.carts = carts;
    }

    public Long execute() {
        return this.carts.stream().mapToLong(Cart::getPrice).sum();
    }
}
