package com.kakao.shopping.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public record OrderDTO(
        int id,
        List<OrderProductDTO> products,
        int totalPrice
) {
}
