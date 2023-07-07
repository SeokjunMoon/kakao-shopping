package com.kakao.shopping.domain.order.dto;

import java.util.List;

public record OrderProductDTO(
        String name,
        List<OrderItemDTO> items
) {
}
