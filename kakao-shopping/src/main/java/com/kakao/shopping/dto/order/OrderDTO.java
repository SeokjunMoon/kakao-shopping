package com.kakao.shopping.dto.order;

import java.util.List;

public record OrderDTO(
        int id,
        List<OrderProductDTO> products,
        int totalPrice
) {
}
