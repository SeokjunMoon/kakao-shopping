package com.kakao.shopping.dto.user;

public record UserLoginRequest(
        String email,
        String password
) {
}
