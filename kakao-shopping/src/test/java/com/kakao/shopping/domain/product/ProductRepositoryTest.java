package com.kakao.shopping.domain.product;

import com.kakao.shopping._core.security.CustomUserDetails;
import com.kakao.shopping.domain.Product;
import com.kakao.shopping.domain.ProductOption;
import com.kakao.shopping.domain.UserAccount;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product Repository Test")
@SpringBootTest
public class ProductRepositoryTest {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;
    private final AuthenticationManager authenticationManager;
    private CustomUserDetails userDetails;

    public ProductRepositoryTest(
            @Autowired ProductRepository productRepository,
            @Autowired OptionRepository optionRepository,
            @Autowired AuthenticationManager authenticationManager
    ) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
        this.authenticationManager = authenticationManager;
    }

    @BeforeEach
    public void setUp() {
        UserLoginRequest request = new UserLoginRequest("moon@naver.com", "qwer1234!");
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        userDetails = (CustomUserDetails) authentication.getPrincipal();
    }

    @DisplayName("product select 테스트")
    @Test
    public void selectTest() {
        // given
        Long productId = 1L;

        // when
        Product product = productRepository.findById(productId).orElseThrow();

        // then
        assertThat(product)
                .extracting("id")
                .isEqualTo(productId);
    }

    @DisplayName("insert 테스트")
    @Test
    public void insertTest() {
        // given
        long previous_count = productRepository.count();
        UserAccount userAccount = userDetails.getUserAccount();
        Product product = Product.of("test", "", "", 1000L, userAccount);

        // when
        productRepository.save(product);

        // then
        assertThat(productRepository.count()).isEqualTo(previous_count + 1);
    }

    @DisplayName("update 테스트")
    @Test
    public void updateTest() {
        // given
        Long productId = 1L;
        String updateName = "update test";
        UserAccount userAccount = userDetails.getUserAccount();
        Product product = productRepository.findById(productId).orElseThrow();
        product.updateName(userAccount, updateName);

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertThat(savedProduct)
                .extracting("name")
                .isEqualTo(updateName);
    }

    @DisplayName("delete 테스트")
    @Test
    public void deleteTest() {
        // given
        Long productId = 1L;
        long previous_count = productRepository.count();
        Product product = productRepository.findById(productId).orElseThrow();
        List<ProductOption> options = optionRepository.findAllByProductId(productId);

        // when
        optionRepository.deleteAll(options);
        productRepository.delete(product);

        // then
        assertThat(productRepository.count()).isEqualTo(previous_count - 1);
    }
}
