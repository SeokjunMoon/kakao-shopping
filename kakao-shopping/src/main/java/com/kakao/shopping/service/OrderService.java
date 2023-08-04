package com.kakao.shopping.service;

import com.kakao.shopping._core.errors.exception.BadRequestException;
import com.kakao.shopping._core.errors.exception.ObjectNotFoundException;
import com.kakao.shopping._core.utils.PriceCalculator;
import com.kakao.shopping.domain.*;
import com.kakao.shopping.dto.order.OrderDTO;
import com.kakao.shopping.dto.order.OrderItemDTO;
import com.kakao.shopping.dto.order.OrderProductDTO;
import com.kakao.shopping.repository.CartRepository;
import com.kakao.shopping.repository.OrderDetailRepository;
import com.kakao.shopping.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;

    public OrderDTO findById(Long orderId, UserAccount userAccount) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderId).orElseThrow(
                () -> new ObjectNotFoundException("존재하지 않는 주문입니다.")
        );

        if (!orderDetail.getUserAccount().equals(userAccount)) {
            throw new BadRequestException("접근할 수 없는 주문내역 입니다.");
        }

        List<OrderItem> items = orderItemRepository.findAllByOrderDetail(orderDetail);
        return toDTO(orderId, items);
    }

    @Transactional
    public OrderDTO save(UserAccount userAccount) {
        List<Cart> carts = cartRepository.findByUserIdOrderByOptionIdAsc(userAccount.getId()).orElseThrow();
        if (carts.isEmpty()) throw new BadRequestException("장바구니가 비어있습니다.");

        cartRepository.deleteAll(carts);

        OrderDetail orderDetail = orderDetailRepository.save(OrderDetail.of(userAccount));
        List<OrderItem> items = getOrderItems(carts, orderDetail);

        return toDTO(orderDetail.getId(), items);
    }

    private static List<OrderItem> getOrderItems(List<Cart> carts, OrderDetail orderDetail) {
        return carts
                .stream()
                .map(cart -> OrderItem.of(orderDetail, cart.getProductOption(), cart.getQuantity(), cart.getPrice()))
                .toList();
    }

    private OrderDTO toDTO(Long orderId, List<OrderItem> items) {
        List<OrderProductDTO> orderProducts = items
                .stream()
                .map(orderItem -> orderItem.getProductOption().getProduct())
                .distinct()
                .map(orderItem -> {
                    List<OrderItemDTO> options = getOrderItemDTOS(items, orderItem);
                    return new OrderProductDTO(orderItem.getName(), options);
                })
                .toList();

        Long totalPrice = PriceCalculator.calculateOrder(items);
        return new OrderDTO(orderId, orderProducts, totalPrice);
    }

    private static List<OrderItemDTO> getOrderItemDTOS(List<OrderItem> items, Product orderItem) {
        return items
                .stream()
                .filter(item -> Objects.equals(item.getProductOption().getProduct().getId(), orderItem.getId()))
                .map(item -> new OrderItemDTO(item.getProductOption().getName(), item.getQuantity(), item.getPrice()))
                .toList();
    }
}
