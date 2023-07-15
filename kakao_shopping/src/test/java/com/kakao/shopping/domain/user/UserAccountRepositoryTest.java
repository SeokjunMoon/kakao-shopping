package com.kakao.shopping.domain.user;

import com.kakao.shopping.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("UserAccount Repository Test")
@DataJpaTest
public class UserAccountRepositoryTest {
    private final UserAccountRepository userAccountRepository;

    public UserAccountRepositoryTest(
            @Autowired UserAccountRepository userAccountRepository
    ) {
        this.userAccountRepository = userAccountRepository;
    }
}
