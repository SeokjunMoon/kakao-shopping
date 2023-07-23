package com.kakao.shopping._core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kakao.shopping.domain.UserAccount;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    public static final Long EXP = 1000L * 60 * 60 * 24;
    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";
    public static final String SECRET = "MySecretKey";

    public static String create(UserAccount userAccount) {
        String token = JWT.create()
                .withSubject(userAccount.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("id", userAccount.getId())
                .withClaim("role", userAccount.getRoles())
                .sign(Algorithm.HMAC512(SECRET));

        return PREFIX + token;
    }

    public static DecodedJWT verify(String token) throws SignatureVerificationException, TokenExpiredException {
        token = token.replace(PREFIX, "");

        return JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(token);
    }
}
