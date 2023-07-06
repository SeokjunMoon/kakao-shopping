package com.kakao.shopping.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemDTO {
    final private String name;
    final private int price;
    final private int quantity;

    @Builder
    public OrderItemDTO(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}
