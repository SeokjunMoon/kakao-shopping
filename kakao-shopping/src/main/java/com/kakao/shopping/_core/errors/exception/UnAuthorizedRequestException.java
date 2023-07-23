package com.kakao.shopping._core.errors.exception;

import com.kakao.shopping._core.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class UnAuthorizedRequestException extends RuntimeException implements CustomException {
    public UnAuthorizedRequestException(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(getMessage(), HttpStatus.UNAUTHORIZED);
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
