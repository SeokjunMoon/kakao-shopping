package com.kakao.shopping._core.errors;

import com.kakao.shopping._core.errors.exception.BadRequestException;
import com.kakao.shopping._core.security.CustomUserDetails;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Aspect
@Component
public class GlobalValidationHandler {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
    }

    @Before("postMapping()")
    public void validationAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof CustomUserDetails userDetails) {
                if (userDetails.getUserAccount() == null) {
                    throw  new BadRequestException("로그인이 필요한 서비스 입니다.");
                }
            }
            if (arg instanceof Errors errors) {
                if (errors.hasErrors()) {
                    throw new BadRequestException(
                            errors.getFieldErrors().get(0).getDefaultMessage()
                                    + ":"
                                    + errors.getFieldErrors().get(0).getField()
                    );
                }
            }
        }
    }
}
