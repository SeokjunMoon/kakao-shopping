package com.kakao.shopping._core.utils;

import com.kakao.shopping.domain.Cart;
import com.kakao.shopping.domain.OrderItem;

import java.util.List;

public class PriceCalculator {
    public static Long calculateOrder(List<OrderItem> items) {
        return items.stream().mapToLong(OrderItem::getPrice).sum();
    }

    public static Long calculateCart(List<Cart> carts) {
        return carts.stream().mapToLong(Cart::getPrice).sum();
    }
}
