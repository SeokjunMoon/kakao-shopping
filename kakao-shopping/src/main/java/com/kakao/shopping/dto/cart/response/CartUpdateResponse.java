package com.kakao.shopping.dto.cart.response;

import com.kakao.shopping.dto.cart.UpdatedCartDTO;

import java.util.List;

public record CartUpdateResponse(
        List<UpdatedCartDTO> carts,
        Long totalPrice
) {
}
