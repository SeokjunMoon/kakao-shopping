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
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserAccount Repository Test")
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yaml"})
@SpringBootTest
public class UserAccountRepositoryTest {
    private final UserAccountRepository userAccountRepository;
    private final EntityManager entityManager;

    public UserAccountRepositoryTest(
            @Autowired UserAccountRepository userAccountRepository,
            @Autowired EntityManager entityManager
            ) {
        this.userAccountRepository = userAccountRepository;
        this.entityManager = entityManager;
    }

    @BeforeEach
    public void setUp() {
        userAccountRepository.save(UserAccount.of("one", "one@naver.com", "qwer1234", LocalDate.of(2000, 9, 15), "ROLES_USER", LocalDateTime.now()));
        entityManager.clear();
    }

    @AfterEach
    public void finish() {
        userAccountRepository.deleteAll();
    }


    @DisplayName("insert 테스트")
    @Test
    public void insertTest() {
        // given
        UserAccount userAccount = UserAccount.of("test", "test@naver.com", "qwer1234", LocalDate.of(2000, 1, 1), "ROLES_USER", LocalDateTime.now());
        long previousUserAccountCount = userAccountRepository.count();

        // when
        userAccountRepository.saveAndFlush(userAccount);

        // then
        assertThat(userAccountRepository.count()).isEqualTo(previousUserAccountCount + 1);
    }

    @DisplayName("select 테스트")
    @Test
    public void selectTest() {
        // given

        // when
        /*
        hibernate_sequence 테이블에서 생성되는 id는 전역으로 관리되어 할당되는 구조이기 때문에
        각 테스트에서 다른 id가 할당된다.

        따라서 ID가 아닌 다른 필드로 조회해야 우리가 원하는 객체에 접근할 수 있다.
         */
        UserAccount userAccount = userAccountRepository.findByEmail("one@naver.com").orElseThrow();

        // then
        assertThat(userAccount)
                .extracting("name")
                .isEqualTo("one");
    }

    @DisplayName("update 테스트")
    @Test
    public void updateTest() {
        // given
        UserAccount userAccount = userAccountRepository.findByEmail("one@naver.com").orElseThrow();

        // when
        userAccount.setName("two");
        UserAccount savedUserAccount = userAccountRepository.saveAndFlush(userAccount);

        // then
        assertThat(savedUserAccount)
                .extracting("name")
                .isEqualTo("two");
    }

    @DisplayName("delete 테스트")
    @Test
    public void deleteTest() {
        // given
        UserAccount userAccount = userAccountRepository.findByEmail("one@naver.com").orElseThrow();
        long previousUserAccountCount = userAccountRepository.count();

        // when
        userAccountRepository.delete(userAccount);

        // then
        assertThat(userAccountRepository.count()).isEqualTo(previousUserAccountCount - 1);
    }
}
