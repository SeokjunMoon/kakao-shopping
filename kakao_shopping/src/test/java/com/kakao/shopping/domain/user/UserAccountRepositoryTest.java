package com.kakao.shopping.domain.user;

import com.kakao.shopping.domain.UserAccount;
import com.kakao.shopping.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserAccount Repository Test")
@DataJpaTest
public class UserAccountRepositoryTest {
    private final UserAccountRepository userAccountRepository;

    public UserAccountRepositoryTest(
            @Autowired UserAccountRepository userAccountRepository
    ) {
        this.userAccountRepository = userAccountRepository;
    }

    @BeforeEach
    public void setUp() {
        userAccountRepository.save(
                UserAccount.of("one", "one@naver.com", "qwer1234", "ROLES_USER", LocalDateTime.now())
        );
    }

    @DisplayName("insert 테스트")
    @Test
    public void insertTest() {
        // given
        UserAccount userAccount = UserAccount.of("test", "test@naver.com", "qwer1234", "ROLES_USER", LocalDateTime.now());
        long previousUserAccountCount = userAccountRepository.count();
        System.out.println("before : " + previousUserAccountCount);

        // when
        userAccountRepository.saveAndFlush(userAccount);
        System.out.println("after : " + userAccountRepository.count());

        // then
        assertThat(userAccountRepository.count()).isEqualTo(previousUserAccountCount + 1);
    }

    @DisplayName("select 테스트")
    @Test
    public void selectTest() {
        // given

        // when
        UserAccount userAccount = userAccountRepository.findById(1L).orElseThrow();

        // then
        assertThat(userAccount)
                .extracting("name")
                .isEqualTo("one");
    }
}
