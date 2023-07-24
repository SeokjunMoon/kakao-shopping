package com.kakao.shopping.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record UserRegisterRequest(
        String name,
        String email,
        String password,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthdate
) {
}
