package com.kakao.shopping.domain.order.dto;

import java.util.List;

public record OrderDTO(
        int id,
        List<OrderProductDTO> products,
        int totalPrice
) {
}
