package com.kakao.shopping.dto.user;

public record UserRegisterRequest(
        String name,
        String email,
        String password
) {
}
