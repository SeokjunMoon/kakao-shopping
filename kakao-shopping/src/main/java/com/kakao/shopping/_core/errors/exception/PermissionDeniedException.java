package com.kakao.shopping._core.errors.exception;

import com.kakao.shopping._core.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class PermissionDeniedException extends RuntimeException implements CustomException {
    public PermissionDeniedException(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(getMessage(), HttpStatus.FORBIDDEN);
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}
