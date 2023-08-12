package com.kakao.shopping._core.utils;

import com.kakao.shopping.domain.OrderItem;

import java.util.List;

public class OrderPriceCalculator {
    private final List<OrderItem> orderItems;

    public OrderPriceCalculator(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Long execute() {
        return this.orderItems.stream().mapToLong(OrderItem::getPrice).sum();
    }
}
