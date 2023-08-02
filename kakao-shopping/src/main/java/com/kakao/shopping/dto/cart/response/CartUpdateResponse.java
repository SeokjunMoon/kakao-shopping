package com.kakao.shopping.dto.cart;

import java.util.List;

public record CartUpdateResponse(
        List<UpdatedCartDTO> carts,
        Long totalPrice
) {
}
