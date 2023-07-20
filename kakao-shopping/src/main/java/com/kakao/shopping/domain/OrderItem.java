package com.kakao.shopping.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    protected OrderItem() {
    }

    private OrderItem(Order order, Option option, int quantity, int price) {
        this.order = order;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    public static OrderItem of(Order order, Option option, int quantity, int price) {
        return new OrderItem(order, option, quantity, price);
    }

    public void update(int quantity) {
        this.quantity = quantity;
        this.price = quantity * this.option.getPrice();
        this.modifiedAt = LocalDateTime.now();
    }
}
