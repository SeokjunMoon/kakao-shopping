package com.kakao.shopping.domain.user;

import com.kakao.shopping.domain.UserAccount;
import com.kakao.shopping.repository.UserAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserAccount Repository Test")
@SpringBootTest
public class UserAccountRepositoryTest {
    private final UserAccountRepository userAccountRepository;

    public UserAccountRepositoryTest(
            @Autowired UserAccountRepository userAccountRepository
    ) {
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("insert 테스트")
    @Test
    public void insertTest() {
        // given
        long previousUserAccountCount = userAccountRepository.count();
        UserAccount userAccount = UserAccount.builder()
                .name("test")
                .email("test@naver.com")
                .password("qwer1234!")
                .birthdate(LocalDate.of(2000, 1, 1))
                .roles("USER")
                .build();

        // when
        userAccountRepository.save(userAccount);

        // then
        assertThat(userAccountRepository.count()).isEqualTo(previousUserAccountCount + 1);
    }

    @DisplayName("select 테스트")
    @Test
    public void selectTest() {
        // given
        Long userId = 1L;

        // when
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow();

        // then
        assertThat(userAccount)
                .extracting("name")
                .isEqualTo("moon");
    }

    @DisplayName("update 테스트")
    @Test
    public void updateTest() {
        // given
        UserAccount userAccount = userAccountRepository.findByEmail("moon@naver.com").orElseThrow();

        // when
        userAccount.updateName("two");
        UserAccount savedUserAccount = userAccountRepository.save(userAccount);

        // then
        assertThat(savedUserAccount)
                .extracting("name")
                .isEqualTo("two");
    }

    // UserAcount는 삭제하지 않는다
    // 주문 기록 또한 데이터 삭제는 발생하지 않음
}
