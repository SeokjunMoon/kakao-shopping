package com.kakao.shopping._core.errors;

import com.kakao.shopping._core.errors.exception.*;
import com.kakao.shopping._core.utils.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException exception) {
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

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> invalidFormat(InvalidFormatException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<?> objectNotFound(ObjectNotFoundException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<?> outOfStock(OutOfStockException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValid(MethodArgumentNotValidException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ApiUtils.error(exception.getFieldError().getDefaultMessage(), status), status);
    }
}
