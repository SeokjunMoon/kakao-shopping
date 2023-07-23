package com.kakao.shopping._core.errors.exception;

import com.kakao.shopping._core.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public interface CustomException {
    ApiUtils.ApiResult<?> body();
    HttpStatus status();
}
