package com.kakao.shopping.domain.order.dto;

public record OrderUpdateRequest(
        int orderId,
        int optionId,
        int quantity
) {
}
