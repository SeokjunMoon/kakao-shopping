package com.kakao.shopping.service;

import com.kakao.shopping.domain.UserAccount;
import com.kakao.shopping.dto.user.UserRegisterRequest;
import com.kakao.shopping.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.InvalidPropertiesFormatException;

@Service
public class UserAccountService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    public UserAccountService(
            @Autowired UserAccountRepository userAccountRepository
    ) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return User.builder()
                .username(userAccount.getEmail())
                .password(userAccount.getPassword())
                .roles(userAccount.getRoles())
                .build();
    }

    public void create(UserRegisterRequest request) throws InvalidPropertiesFormatException, DuplicateKeyException {
        checkEmailFormat(request.email());
        checkNameFormat(request.name());
        checkPasswordFormat(request.password());

        try {
            userAccountRepository.save(
                    UserAccount.of(
                            request.name(),
                            request.email(),
                            request.password(),
                            request.birthdate()
                    )
            );
        }
        catch (DataIntegrityViolationException error) {
            throw new DuplicateKeyException("중복된 email 입니다.");
        }
    }

    private void checkEmailFormat(String email) throws InvalidPropertiesFormatException {
        if (email == null || email.length() < 1 || email.length() > 100) {
            throw new InvalidPropertiesFormatException("이메일의 길이는 1 이상 100 이하만 가능합니다.");
        }

        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(regex)) {
            throw new InvalidPropertiesFormatException("이메일 형식이 잘못되었습니다.");
        }
    }

    private void checkNameFormat(String name) throws InvalidPropertiesFormatException {
        if (name.length() > 45) {
            throw new InvalidPropertiesFormatException("이름의 길이는 45자 이하만 가능합니다.");
        }
    }

    private void checkPasswordFormat(String password) throws InvalidPropertiesFormatException {
        if (password == null || password.length() < 1 || password.length() > 256) {
            throw new InvalidPropertiesFormatException("비밀번호의 길이는 1 이상 256 이하만 가능합니다.");
        }

        String regex = "^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$";
        if (!password.matches(regex)) {
            throw new InvalidPropertiesFormatException("비밀번호는 1개 이상의 소문자, 1개 이상의 대문자, 1개 이상의 특수문자를 포함해야 합니다.");
        }
    }
}
