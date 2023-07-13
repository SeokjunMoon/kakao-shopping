package com.kakao.shopping.dto.order;

public record OrderUpdateRequest(
        int orderId,
        int optionId,
        int quantity
) {
}
