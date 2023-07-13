package com.kakao.shopping.dto;

import java.util.List;

public record OrderProductDTO(
        String name,
        List<OrderItemDTO> items
) {
}
