package com.kakao.shopping.controller;

import com.kakao.shopping._core.utils.ApiUtils;
import com.kakao.shopping.dto.UserRegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
