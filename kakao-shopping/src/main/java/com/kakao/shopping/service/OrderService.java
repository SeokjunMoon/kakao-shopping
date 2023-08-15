package com.kakao.shopping.service;

import com.kakao.shopping._core.errors.exception.BadRequestException;
import com.kakao.shopping._core.errors.exception.ObjectNotFoundException;
import com.kakao.shopping._core.errors.exception.OutOfStockException;
import com.kakao.shopping._core.utils.calculator.OrderPriceCalculator;
import com.kakao.shopping._core.utils.calculator.PriceCalculator;
import com.kakao.shopping.domain.*;
import com.kakao.shopping.dto.order.OrderDTO;
import com.kakao.shopping.dto.order.OrderItemDTO;
import com.kakao.shopping.dto.order.OrderProductDTO;
import com.kakao.shopping.repository.CartRepository;
import com.kakao.shopping.repository.OptionRepository;
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
    private final OptionRepository optionRepository;

    public OrderDTO findById(Long orderId, UserAccount userAccount) {
        OrderDetail orderDetail = getOrderDetail(orderId, userAccount);
        List<OrderItem> items = orderItemRepository.findAllByOrderDetail(orderDetail);
        return toDTO(orderId, items);
    }

    @Transactional
    public OrderDTO save(UserAccount userAccount) {
        List<Cart> carts = cartRepository.findAllByUserAccountId(userAccount.getId())
                .orElseThrow(() -> new BadRequestException("장바구니가 비어있습니다."));

        checkStock(carts);
        cartRepository.deleteAll(carts);

        carts.forEach(cart -> {
            ProductOption option = cart.getProductOption();
            Long stock = option.getStock() - cart.getQuantity();
            option.updateStock(userAccount, stock);
            optionRepository.save(option);
        });

        OrderDetail orderDetail = orderDetailRepository.save(OrderDetail.of(userAccount));
        List<OrderItem> items = getOrderItems(carts, orderDetail);

        orderItemRepository.saveAll(items);
        return toDTO(orderDetail.getId(), items);
    }

    private OrderDetail getOrderDetail(Long orderId, UserAccount userAccount) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderId)
                .orElseThrow(() -> new ObjectNotFoundException("존재하지 않는 주문입니다."));

        if (!orderDetail.getUserAccount().equals(userAccount)) {
            throw new BadRequestException("접근할 수 없는 주문내역 입니다.");
        }
        return orderDetail;
    }

    private static void checkStock(List<Cart> carts) {
        carts.forEach(cart -> {
            if (cart.getProductOption().getStock() < cart.getQuantity()) {
                throw new OutOfStockException("재고가 부족합니다.");
            }
        });
    }

    private static List<OrderItem> getOrderItems(List<Cart> carts, OrderDetail orderDetail) {
        return carts
                .stream()
                .map(cart -> OrderItem.of(orderDetail, cart.getProductOption(), cart.getQuantity(), cart.getPrice()))
                .toList();
    }

    private static OrderDTO toDTO(Long orderId, List<OrderItem> items) {
        List<OrderProductDTO> orderProducts = items
                .stream()
                .map(orderItem -> orderItem.getProductOption().getProduct())
                .distinct()
                .map(orderItem -> {
                    List<OrderItemDTO> options = getOrderItemDTOS(items, orderItem);
                    return new OrderProductDTO(orderItem.getName(), options);
                })
                .toList();

        PriceCalculator calculator = new OrderPriceCalculator(items);
        Long totalPrice = calculator.execute();
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
