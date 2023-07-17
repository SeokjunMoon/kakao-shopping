package com.kakao.shopping.dto.order;

import java.util.List;

public record OrderProductDTO(
        String name,
        List<OrderItemDTO> items
) {
}
