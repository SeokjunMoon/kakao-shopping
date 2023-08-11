package com.kakao.shopping.domain.cart;

import com.kakao.shopping._core.security.CustomUserDetails;
import com.kakao.shopping.domain.Cart;
import com.kakao.shopping.domain.ProductOption;
import com.kakao.shopping.dto.user.UserLoginRequest;
import com.kakao.shopping.repository.CartRepository;
import com.kakao.shopping.repository.OptionRepository;
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

@DisplayName("Cart Repository Test")
@SpringBootTest
public class CartRepositoryTest {
    private final CartRepository cartRepository;
    private final OptionRepository optionRepository;
    private final AuthenticationManager authenticationManager;
    private CustomUserDetails userDetails;

    public CartRepositoryTest(
            @Autowired CartRepository cartRepository,
            @Autowired OptionRepository optionRepository,
            @Autowired AuthenticationManager authenticationManager
    ) {
        this.cartRepository = cartRepository;
        this.optionRepository = optionRepository;
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

    @DisplayName("insert test")
    @Test
    public void insert_test() {
        // given
        long previous_count = cartRepository.count();
        ProductOption option = optionRepository.findById(1L).orElseThrow();
        Long quantity = 5L;
        Cart cart = Cart.builder()
                .userAccount(userDetails.getUserAccount())
                .productOption(option)
                .quantity(quantity)
                .build();

        // when
        cartRepository.save(cart);

        // then
        assertThat(cartRepository.count()).isEqualTo(previous_count + 1);
    }

    @DisplayName("select test")
    @Test
    public void select_test() {
        // given
        Long userId = userDetails.getUserAccount().getId();

        // when
        List<Cart> savedCart = cartRepository.findByUserIdOrderByOptionIdAsc(userId).orElseThrow();

        // then
        assertThat(savedCart.get(0))
                .extracting("productOption")
                .extracting("name")
                .isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    }

    @DisplayName("update test")
    @Test
    public void update_test() {
        // given
        Long userId = userDetails.getUserAccount().getId();
        List<Cart> carts = cartRepository.findByUserIdOrderByOptionIdAsc(userId).orElseThrow();
        Cart cart = carts.get(0);

        // when
        cart.updateQuantity(10L);
        Cart savedCart = cartRepository.save(cart);

        // then
        assertThat(savedCart)
                .extracting("quantity")
                .isEqualTo(10L);
    }

    @DisplayName("delete test")
    @Test
    public void delete_test() {
        // given
        long previous_count = cartRepository.count();
        Long userId = userDetails.getUserAccount().getId();
        List<Cart> carts = cartRepository.findByUserIdOrderByOptionIdAsc(userId).orElseThrow();
        Cart cart = carts.get(0);

        // when
        cartRepository.delete(cart);

        // then
        assertThat(cartRepository.count()).isEqualTo(previous_count - 1);
    }
}
