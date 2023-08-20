package com.kakao.shopping._core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.shopping._core.errors.exception.CustomException;
import com.kakao.shopping._core.errors.exception.PermissionDeniedException;
import com.kakao.shopping._core.errors.exception.UnauthorizedRequestException;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /*
    PasswordEncoder는 비밀번호를 단방향으로 함호화하는 기능을 포함하는 인터페이스 이다.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /*
    정적 자원에 대해서 security 제외
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // 서버에 인증 정보를 저장하지 않기에 csrf를 사용하지 않음
                .csrf().disable()

                // form 로그인 해제
                .formLogin().disable()

                .httpBasic().disable()

                // cors 재설정
                .cors().configurationSource(configurationSource())

                .and()
                .apply(new CustomSecurityFilterManager())

                // 인증 요청 URL 별로 인증 및 ROLE 설정을 한다.
                .and()
                .authorizeRequests()
                .mvcMatchers("/cart/**", "/option/**", "/order/**", "/user/**").authenticated()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()

                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
                    createErrorResponse(response, new UnauthorizedRequestException("인증되지 않은 요청입니다."));
                })

                .and()
                .exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
                    createErrorResponse(response, new PermissionDeniedException("권한이 없습니다."));
                })

                .and()
                .logout().logoutSuccessUrl("/")

                .and()
                .oauth2Login()

                .successHandler()

                .userInfoEndpoint()

                .userService()

                .and().build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // 모든 Http Method 허용
        configuration.addAllowedOriginPattern("*"); // 모든 IP 허용
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); //

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", configuration);
        return configurationSource;
    }

    public static void createErrorResponse(HttpServletResponse response, CustomException exception) throws IOException {
        response.setStatus(exception.status().value());
        response.setContentType("application/json; charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(exception.body());
        response.getWriter().println(responseBody);
    }

    public static class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            super.configure(builder);
        }
    }
}
