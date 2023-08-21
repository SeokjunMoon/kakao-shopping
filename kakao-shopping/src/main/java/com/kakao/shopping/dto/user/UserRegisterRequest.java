package com.kakao.shopping.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public record UserRegisterRequest(
        @NotNull(message = "이름을 입력해주세요.")
        @Size(min = 2, max = 45, message = "이름은 2~45자 사이만 가능합니다.")
        String name,

        @NotNull(message = "이메일을 입력해주세요.")
        @Size(min = 10, max = 100, message = "이메일은 10~100자 사이만 가능합니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        String email,

        @NotNull(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 256, message = "비밀번호는 8~256자 사이만 가능합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
                 message = "비밀번호는 1개 이상의 영문자, 1개 이상의 숫자, 1개 이상의 특수문자를 포함해야 합니다.")
        String password,

        @NotNull(message = "생년월일을 입력해주세요.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthdate
) {
}
