package com.kakao.shopping.controller;

import com.kakao.shopping._core.errors.exception.BadRequestException;
import com.kakao.shopping._core.security.JwtTokenProvider;
import com.kakao.shopping._core.utils.ApiUtils;
import com.kakao.shopping.dto.user.UserLoginRequest;
import com.kakao.shopping.dto.user.UserRegisterRequest;
import com.kakao.shopping.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRegisterRequest userRegisterRequest, Errors error, HttpServletRequest request) throws InvalidPropertiesFormatException {
        ResponseEntity<? extends ApiUtils.ApiResult<?>> exception = checkErrors(error);
        if (exception != null) return exception;

        userAccountService.register(userRegisterRequest);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest, Errors error, HttpServletRequest request) {
        ResponseEntity<? extends ApiUtils.ApiResult<?>> exception = checkErrors(error);
        if (exception != null) return exception;

        String jwtToken = userAccountService.login(loginRequest);
        return ResponseEntity
                .ok()
                .header(JwtTokenProvider.HEADER, jwtToken)
                .body(ApiUtils.success(null));
    }

    private static ResponseEntity<? extends ApiUtils.ApiResult<?>> checkErrors(Errors error) {
        if (error.hasErrors()) {
            List<FieldError> errors = error.getFieldErrors();
            BadRequestException exception = new BadRequestException(
                    errors.get(0).getDefaultMessage() + " : " + errors.get(0).getField()
            );
            return new ResponseEntity<>(
                    exception.body(),
                    exception.status()
            );
        }
        return null;
    }
}
