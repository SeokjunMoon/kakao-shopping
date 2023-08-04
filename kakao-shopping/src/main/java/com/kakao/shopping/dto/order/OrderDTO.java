package com.kakao.shopping.dto.order;

import java.util.List;

public record OrderDTO(
        Long id,
        List<OrderProductDTO> products,
        Long totalPrice
) {
}
