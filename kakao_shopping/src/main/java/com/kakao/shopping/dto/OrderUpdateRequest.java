package com.kakao.shopping.dto;

public record OrderUpdateRequest(
        int orderId,
        int optionId,
        int quantity
) {
}
