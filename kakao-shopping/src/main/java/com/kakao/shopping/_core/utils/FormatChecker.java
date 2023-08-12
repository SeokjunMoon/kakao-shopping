package com.kakao.shopping._core.utils;

import com.kakao.shopping._core.errors.exception.BadRequestException;
import com.kakao.shopping._core.errors.exception.InvalidFormatException;
import com.kakao.shopping.dto.user.UserLoginRequest;
import com.kakao.shopping.dto.user.UserRegisterRequest;

public class FormatChecker {
    enum TYPE {REGISTER, LOGIN};
    private final String name;
    private final String email;
    private final String password;
    private final TYPE type;

    public FormatChecker(UserRegisterRequest request) {
        this.type = TYPE.REGISTER;
        this.name = request.name();
        this.email = request.email();
        this.password = request.password();
    }

    public FormatChecker(UserLoginRequest request) {
        this.type = TYPE.LOGIN;
        this.email = request.email();
        this.name = null;
        this.password = request.password();
    }

    public void execute() {
        checkEmailFormat();
        checkPasswordFormat();

        if (this.type == TYPE.REGISTER) {
            checkNameFormat();
        }
    }

    private void checkNameFormat() {
        if (this.name == null) {
            throw new BadRequestException("이름을 입력해주세요.");
        }
        if (this.name.length() > 45) {
            throw new InvalidFormatException("이름의 길이는 45자 이하만 가능합니다.");
        }
    }

    private void checkEmailFormat() {
        if (this.email == null) {
            throw new BadRequestException("이메일을 입력해주세요.");
        }
        if (this.email.length() < 1 || this.email.length() > 100) {
            throw new InvalidFormatException("이메일은 1자 이상 100자 이하만 가능합니다.");
        }

        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!this.email.matches(regex)) {
            throw new InvalidFormatException("이메일 형식이 잘못되었습니다.");
        }
    }

    private void checkPasswordFormat() {
        if (this.password == null) {
            throw new BadRequestException("비밀번호를 입력해주세요.");
        }
        if (this.password.length() < 8 || this.password.length() > 256) {
            throw new InvalidFormatException("비밀번호의 길이는 8 이상 256 이하만 가능합니다.");
        }

        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
        if (!this.password.matches(regex)) {
            throw new InvalidFormatException("비밀번호는 1개 이상의 영문자, 1개 이상의 숫자, 1개 이상의 특수문자를 포함해야 합니다.");
        }
    }
}
