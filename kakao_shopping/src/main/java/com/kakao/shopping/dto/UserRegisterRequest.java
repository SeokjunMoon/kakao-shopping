package com.kakao.shopping.dto;

public record UserRegisterRequest(
        String name,
        String email,
        String password
) {
}
