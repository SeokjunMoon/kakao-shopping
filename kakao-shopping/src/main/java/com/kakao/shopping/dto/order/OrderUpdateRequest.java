package com.kakao.shopping.dto.order;

public record OrderUpdateRequest(
        Long orderId,
        Long optionId,
        Long quantity
) {
}
