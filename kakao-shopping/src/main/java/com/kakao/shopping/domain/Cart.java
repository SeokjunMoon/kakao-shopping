package com.kakao.shopping.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    protected Cart() {
    }

    private Cart(UserAccount userAccount, Option option, int quantity, int price) {
        this.userAccount = userAccount;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }

    public static Cart of(UserAccount userAccount, Option option, int quantity, int price) {
        return new Cart(userAccount, option, quantity, price);
    }
}
