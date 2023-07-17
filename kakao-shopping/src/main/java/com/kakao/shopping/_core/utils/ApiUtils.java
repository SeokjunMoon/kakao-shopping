package com.kakao.shopping._core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class ApiUtils {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class ApiResult<T> {
        final private boolean success;
        final private T response;
        final private ApiError error;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ApiError<T> {
        final private String message;
        final private int status;
    }

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> error (String message, HttpStatus httpStatus) {
        return new ApiResult<>(false, null, new ApiError<>(message, httpStatus.value()));
    }
}
