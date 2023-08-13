package com.kakao.shopping.dto.order.request;

import javax.validation.constraints.Min;

public record OrderUpdateRequest(
        @Min(1) Long orderId,
        @Min(1) Long optionId,
        @Min(1) Long quantity,
        String reason
) {
}
