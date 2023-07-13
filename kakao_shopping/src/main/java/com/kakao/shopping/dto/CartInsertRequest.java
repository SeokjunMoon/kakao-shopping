package com.kakao.shopping.dto;

public record CartInsertRequest(
        int optionId,
        int quantity
) {
}
