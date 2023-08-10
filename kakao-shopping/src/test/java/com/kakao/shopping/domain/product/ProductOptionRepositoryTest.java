package com.kakao.shopping.domain.product;

import com.kakao.shopping._core.security.CustomUserDetails;
import com.kakao.shopping.domain.Product;
import com.kakao.shopping.domain.ProductOption;
import com.kakao.shopping.dto.user.UserLoginRequest;
import com.kakao.shopping.repository.OptionRepository;
import com.kakao.shopping.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductOption Test")
@SpringBootTest
public class ProductOptionRepositoryTest {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;
    private final AuthenticationManager authenticationManager;
    private CustomUserDetails userDetails;

    public ProductOptionRepositoryTest(
            @Autowired OptionRepository optionRepository,
            @Autowired ProductRepository productRepository,
            @Autowired AuthenticationManager authenticationManager
    ) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
        this.authenticationManager = authenticationManager;
    }

    @BeforeEach
    public void setUp() {
        UserLoginRequest request = new UserLoginRequest("moon@naver.com", "qwer1234!");
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(token);
        userDetails = (CustomUserDetails) authentication.getPrincipal();
    }

    @DisplayName("insert 테스트")
    @Test
    public void insert_test() {
        // given
        long previous_count = optionRepository.count();
        Product product = productRepository.findById(1L).orElseThrow();
        ProductOption option = ProductOption.of(product, "test", 1000L, userDetails.getUserAccount());

        // when
        optionRepository.save(option);

        // then
        assertThat(optionRepository.count()).isEqualTo(previous_count + 1);
    }

    @DisplayName("select 테스트")
    @Test
    public void select_test() {
        // given
        Long optionId = 10L;

        // when
        ProductOption option = optionRepository.findById(optionId).orElseThrow();

        // then
        assertThat(option)
                .extracting("name")
                .isEqualTo("JR310BT (무선 전용) - 레드");
    }

    @DisplayName("update 테스트")
    @Test
    public void update_test() {
        // given
        Long optionId = 15L;
        ProductOption option = optionRepository.findById(optionId).orElseThrow();
        String test_name = "update test";
        option.updateName(userDetails.getUserAccount(), test_name);

        // when
        ProductOption savedOption = optionRepository.save(option);

        // then
        assertThat(savedOption)
                .extracting("name")
                .isEqualTo(test_name);
    }

    @DisplayName("delete 테스트")
    @Test
    public void delete_test() {
        // given
        long previous_count = optionRepository.count();
        Long optionId = 1L;
        ProductOption option = optionRepository.findById(optionId).orElseThrow();

        // when
        optionRepository.delete(option);

        // then
        assertThat(optionRepository.count()).isEqualTo(previous_count - 1);
    }
}
