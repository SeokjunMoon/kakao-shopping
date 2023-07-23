package com.kakao.shopping.dto.user;

import java.time.LocalDate;

public record UserRegisterRequest(
        String name,
        String email,
        String password,
        LocalDate birthdate
) {
}
