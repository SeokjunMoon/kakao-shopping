package com.kakao.shopping.service;

import com.kakao.shopping._core.errors.exception.BadRequestException;
import com.kakao.shopping._core.errors.exception.ObjectNotFoundException;
import com.kakao.shopping.domain.Cart;
import com.kakao.shopping.domain.ProductOption;
import com.kakao.shopping.domain.UserAccount;
import com.kakao.shopping.dto.cart.*;
import com.kakao.shopping.repository.CartRepository;
import com.kakao.shopping.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final OptionRepository optionRepository;

    @Transactional
    public void addCartList(List<CartInsertRequest> requests, UserAccount sessionUser) {
        List<Long> ids = requests.stream().map(CartInsertRequest::optionId).distinct().toList();
        if (ids.size() != requests.size()) {
            throw new BadRequestException("잘못된 요청입니다. 요청에서 중복이 발생하였습니다.");
        }

        List<ProductOption> options = optionRepository.findAllById(ids);
        List<Cart> savedCarts = cartRepository.findByUserIdOrderByOptionIdAsc(sessionUser.getId()).orElse(null);

        requests
                .forEach(request -> {
                    if (request.quantity() < 0) {
                        throw new BadRequestException("잘못된 요청입니다. 수량은 음수가 될 수 없습니다.");
                    }

                    Cart cart = savedCarts
                            .stream()
                            .filter(cart1 -> Objects.equals(cart1.getProductOption().getId(), request.optionId()))
                            .findFirst()
                            .orElseGet(() -> {
                                ProductOption option = options
                                        .stream()
                                        .filter(option1 -> Objects.equals(option1.getId(), request.optionId()))
                                        .findAny()
                                        .orElseThrow(() -> new ObjectNotFoundException("해당 옵션을 찾을 수 없습니다."));

                                return Cart.builder()
                                        .userAccount(sessionUser)
                                        .productOption(option)
                                        .quantity(0L)
                                        .price(0L)
                                        .build();
                            });

                    Long quantity = cart.getQuantity() + request.quantity();

                    cart.updateQuantity(quantity);
                    cartRepository.save(cart);
                });
    }

    public CartDTO findAll(UserAccount user) {
        List<Cart> carts = cartRepository.findByUserIdOrderByOptionIdAsc(user.getId()).orElse(null);

        Long totalPrice = carts.stream().mapToLong(Cart::getPrice).sum();

        List<CartProductDTO> products = carts
                .stream()
                .map(cart -> cart.getProductOption().getProduct())
                .distinct()
                .map(product -> {
                    List<CartItemDTO> items = carts
                            .stream()
                            .filter(cart -> cart.getProductOption().getProduct().getId().equals(product.getId()))
                            .map(item -> {
                                ProductOption option = item.getProductOption();
                                CartProductOptionDTO optionDTO = new CartProductOptionDTO(option.getId(), option.getName(), option.getPrice());
                                return new CartItemDTO(option.getId(), optionDTO, item.getQuantity(), item.getPrice());
                            })
                            .toList();
                    return new CartProductDTO(product.getId(), product.getName(), items);
                })
                .toList();

        return new CartDTO(products, totalPrice);
    }

    @Transactional
    public CartUpdateResponse update(List<CartUpdateRequest> requests, UserAccount user) {
        if (requests.isEmpty()) {
            throw new BadRequestException("장바구니 업데이트 요청이 비어있습니다.");
        }

        List<Long> sets = requests.stream().map(CartUpdateRequest::cartId).distinct().toList();
        if (sets.size() != requests.size()) {
            throw new BadRequestException("잘못된 요청입니다. 요청에서 중복이 발생하였습니다.");
        }

        List<Cart> savedCarts = cartRepository.findByUserIdOrderByOptionIdAsc(user.getId()).orElseThrow(
                () -> new ObjectNotFoundException("장바구니가 비어있습니다.")
        );
        List<Cart> carts = requests
                .stream()
                .map(request -> {
                    Cart cart = savedCarts
                            .stream()
                            .filter(cart1 -> Objects.equals(cart1.getId(), request.cartId()))
                            .findAny()
                            .orElseThrow(() -> new ObjectNotFoundException("해당 장바구니를 찾을 수 없습니다."));

                    Long quantity = request.quantity();

                    if (quantity < 0) {
                        throw new BadRequestException("잘못된 요청입니다. 수량은 음수가 될 수 없습니다.");
                    }

                    cart.updateQuantity(quantity);

                    return cart;
                })
                .toList();

        cartRepository.saveAll(carts);

        Long totalPrice = carts.stream().mapToLong(Cart::getPrice).sum();
        List<UpdatedCartDTO> updatedCarts = carts
                .stream()
                .map(cart -> new UpdatedCartDTO(cart.getId(), cart.getProductOption().getId(), cart.getProductOption().getName(), cart.getQuantity(), cart.getPrice()))
                .toList();

        return new CartUpdateResponse(updatedCarts, totalPrice);
    }
}
