package com.kakao.shopping.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderProductDTO {
    final private String name;
    final private List<OrderItemDTO> items;

    @Builder
    public OrderProductDTO(String name, List<OrderItemDTO> items) {
        this.name = name;
        this.items = items;
    }
}
