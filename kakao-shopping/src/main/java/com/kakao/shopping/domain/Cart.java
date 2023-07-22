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
    private ProductOption productOption;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    protected Cart() {
    }

    private Cart(UserAccount userAccount, ProductOption productOption, int quantity, int price) {
        this.userAccount = userAccount;
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = price;
    }

    public static Cart of(UserAccount userAccount, ProductOption productOption, int quantity, int price) {
        return new Cart(userAccount, productOption, quantity, price);
    }
}
