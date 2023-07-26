package com.kakao.shopping._core.errors;

import com.kakao.shopping._core.errors.exception.BadRequestException;
import com.kakao.shopping._core.errors.exception.PasswordMismatchException;
import com.kakao.shopping._core.errors.exception.PermissionDeniedException;
import com.kakao.shopping._core.errors.exception.UnauthorizedRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<?> passwordMismatch(PasswordMismatchException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<?> permissionDenied(PermissionDeniedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    public ResponseEntity<?> unauthorized(UnauthorizedRequestException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
