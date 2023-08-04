package com.kakao.shopping.repository;

import com.kakao.shopping.domain.OrderDetail;
import com.kakao.shopping.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderDetail(OrderDetail orderDetail);
}
