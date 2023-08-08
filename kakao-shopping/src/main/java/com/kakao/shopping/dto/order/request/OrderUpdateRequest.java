package com.kakao.shopping.dto.order.request;

public record OrderUpdateRequest(
        Long orderId,
        Long optionId,
        Long quantity
) {
}
