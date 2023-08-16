package com.kakao.shopping.controller;

import com.kakao.shopping._core.security.JwtTokenProvider;
import com.kakao.shopping._core.utils.ApiUtils;
import com.kakao.shopping.dto.user.UserLoginRequest;
import com.kakao.shopping.dto.user.UserRegisterRequest;
import com.kakao.shopping.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.InvalidPropertiesFormatException;

@RequiredArgsConstructor
@RestController
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("/join")
    public ResponseEntity<?> join(
            @Valid @RequestBody UserRegisterRequest userRegisterRequest,
            Errors error
    ) {
        userAccountService.register(userRegisterRequest);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLoginRequest loginRequest,
            Errors error
    ) {
        String jwtToken = userAccountService.login(loginRequest);
        return ResponseEntity
                .ok()
                .header(JwtTokenProvider.HEADER, jwtToken)
                .body(ApiUtils.success(null));
    }
}
