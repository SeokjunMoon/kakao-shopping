package com.kakao.shopping.dto.user;

import javax.validation.constraints.Email;

public record UserLoginRequest(
        @Email String email,
        String password
) {
}
