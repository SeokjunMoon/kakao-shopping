package com.kakao.shopping.service;

import com.kakao.shopping._core.errors.exception.BadRequestException;
import com.kakao.shopping._core.errors.exception.ObjectNotFoundException;
import com.kakao.shopping._core.errors.exception.PermissionDeniedException;
import com.kakao.shopping._core.utils.calculator.CartPriceCalculator;
import com.kakao.shopping._core.utils.calculator.PriceCalculator;
import com.kakao.shopping.domain.Cart;
import com.kakao.shopping.domain.Product;
import com.kakao.shopping.domain.ProductOption;
import com.kakao.shopping.domain.UserAccount;
import com.kakao.shopping.dto.cart.*;
import com.kakao.shopping.dto.cart.request.CartDeleteRequest;
import com.kakao.shopping.dto.cart.request.CartInsertRequest;
import com.kakao.shopping.dto.cart.request.CartUpdateRequest;
import com.kakao.shopping.dto.cart.response.CartUpdateResponse;
import com.kakao.shopping.repository.CartRepository;
import com.kakao.shopping.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final OptionRepository optionRepository;

    public CartDTO findAll(UserAccount user) {
        List<Cart> carts = cartRepository.findByUserIdOrderByOptionIdAsc(user.getId()).orElse(null);

        PriceCalculator calculator = new CartPriceCalculator(carts);
        Long totalPrice = calculator.execute();

        List<CartProductDTO> products = toCartProductDTO(carts);
        return new CartDTO(products, totalPrice);
    }

    @Transactional
    public void addCartList(List<CartInsertRequest> requests, UserAccount userAccount) {
        List<Long> ids = requests.stream().map(CartInsertRequest::optionId).distinct().toList();
        checkRequestValidation(requests.size(), ids.size());

        List<ProductOption> options = optionRepository.findAllByIdIn(ids);
        List<Cart> savedCarts = cartRepository.findByUserIdOrderByOptionIdAsc(userAccount.getId()).orElse(null);

        requests.forEach(request -> {
            Cart cart = getCartById(userAccount, options, savedCarts, request.optionId());
            Long quantity = cart.getQuantity() + request.quantity();
            cart.updateQuantity(quantity);
            cartRepository.save(cart);
        });
    }

    @Transactional
    public CartUpdateResponse update(List<CartUpdateRequest> requests, UserAccount user) {
        List<Long> ids = requests.stream().map(CartUpdateRequest::cartId).distinct().toList();
        checkRequestValidation(requests.size(), ids.size());

        List<Cart> savedCarts = cartRepository.findByUserIdOrderByOptionIdAsc(user.getId())
                .orElseThrow(() -> new ObjectNotFoundException("장바구니가 비어있습니다."));

        List<Cart> carts = requests
                .stream()
                .map(request -> {
                    Cart cart = getCartById(savedCarts, request.cartId());
                    Long quantity = request.quantity();
                    cart.updateQuantity(quantity);
                    return cart;
                })
                .toList();
        cartRepository.saveAll(carts);

        PriceCalculator calculator = new CartPriceCalculator(carts);
        Long totalPrice = calculator.execute();

        List<UpdatedCartDTO> updatedCarts = toUpdatedCartDTO(carts);
        return new CartUpdateResponse(updatedCarts, totalPrice);
    }

    public void delete(List<CartDeleteRequest> requests, UserAccount userAccount) {
        List<Long> ids = requests.stream().map(CartDeleteRequest::cartId).toList();
        List<Cart> carts = cartRepository.findAllById(ids);
        carts.forEach(cart -> {
            if (!cart.getUserAccount().equals(userAccount)) {
                throw new PermissionDeniedException("해당 계정으로 접근할 수 없는 장바구니 입니다.");
            }
        });
        cartRepository.deleteAll(carts);
    }

    // ------------------------------------------------------------------------------------------

    private static void checkRequestValidation(int requestCount, int idCount) {
        if (!Objects.equals(requestCount, idCount)) {
            throw new BadRequestException("잘못된 요청입니다. 요청에서 중복이 발생했습니다.");
        }
    }

    private static Cart getCartById(UserAccount userAccount, List<ProductOption> options, List<Cart> savedCarts, Long id) {
        return savedCarts
                .stream()
                .filter(cart -> Objects.equals(cart.getProductOption().getId(), id))
                .findFirst()
                .orElseGet(() -> {
                    ProductOption option = getProductOptionById(options, id);
                    return Cart.builder()
                            .userAccount(userAccount)
                            .productOption(option)
                            .quantity(0L)
                            .build();
                });
    }

    private static Cart getCartById(List<Cart> savedCarts, Long id) {
        return savedCarts
                .stream()
                .filter(cart1 -> Objects.equals(cart1.getId(), id))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("해당 장바구니를 찾을 수 없습니다."));
    }

    private static ProductOption getProductOptionById(List<ProductOption> options, Long id) {
        return options
                .stream()
                .filter(option1 -> Objects.equals(option1.getId(), id))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("해당 옵션을 찾을 수 없습니다."));
    }

    private static List<CartProductDTO> toCartProductDTO(List<Cart> carts) {
        return carts
                .stream()
                .map(cart -> cart.getProductOption().getProduct())
                .distinct()
                .map(product -> {
                    List<CartItemDTO> items = toCartItemDTO(carts, product);
                    return new CartProductDTO(product.getId(), product.getName(), items);
                })
                .toList();
    }

    private static List<CartItemDTO> toCartItemDTO(List<Cart> carts, Product product) {
        return carts
                .stream()
                .filter(cart -> cart.getProductOption().getProduct().getId().equals(product.getId()))
                .map(item -> {
                    ProductOption option = item.getProductOption();
                    CartProductOptionDTO optionDTO = new CartProductOptionDTO(option.getId(), option.getName(), option.getPrice());
                    return new CartItemDTO(option.getId(), optionDTO, item.getQuantity(), item.getPrice());
                })
                .toList();
    }

    private static List<UpdatedCartDTO> toUpdatedCartDTO(List<Cart> carts) {
        return carts
                .stream()
                .map(cart -> new UpdatedCartDTO(cart.getId(), cart.getProductOption().getId(), cart.getProductOption().getName(), cart.getQuantity(), cart.getPrice()))
                .toList();
    }
}
