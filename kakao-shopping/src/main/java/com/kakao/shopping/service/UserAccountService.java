package com.kakao.shopping.service;

import com.kakao.shopping._core.errors.exception.BadRequestException;
import com.kakao.shopping._core.security.CustomUserDetails;
import com.kakao.shopping._core.security.JwtTokenProvider;
import com.kakao.shopping.domain.UserAccount;
import com.kakao.shopping.dto.user.UserLoginRequest;
import com.kakao.shopping.dto.user.UserRegisterRequest;
import com.kakao.shopping.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAccountService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return new CustomUserDetails(userAccount);
    }

    public UserAccount register(UserRegisterRequest request) {
        try {
            return userAccountRepository.save(toEntity(request));
        }
        catch (DataIntegrityViolationException error) {
            throw new BadRequestException("중복된 email 입니다.");
        }
    }

    public String login(UserLoginRequest request) {
        UserAccount userAccount = userAccountRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadRequestException("등록되지 않은 이메일 입니다."));

        if (!passwordEncoder.matches(request.password(), userAccount.getPassword())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }

        return JwtTokenProvider.create(userAccount);
    }

    private UserAccount toEntity(UserRegisterRequest request) {
        return UserAccount.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .birthdate(request.birthdate())
                .build();
    }

    public UserAccount findByEmailAndProvider(String email, String provider) {
        return userAccountRepository.findByEmailAndProvider(email, provider)
                .orElseThrow(() -> new BadRequestException("해당 유저를 찾을 수 없습니다."));
    }
}
