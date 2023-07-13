package com.kakao.shopping.dto;

import java.util.List;

public record OrderDTO(
        int id,
        List<OrderProductDTO> products,
        int totalPrice
) {
}
