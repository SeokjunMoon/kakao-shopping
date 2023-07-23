package com.kakao.shopping._core.errors.exception;

import com.kakao.shopping._core.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends RuntimeException implements CustomException {
    public PasswordMismatchException(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(getMessage(), HttpStatus.BAD_REQUEST);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
