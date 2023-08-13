package com.kakao.shopping.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Email;
import java.time.LocalDate;

public record UserRegisterRequest(
        String name,
        @Email String email,
        String password,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthdate
) {
}
