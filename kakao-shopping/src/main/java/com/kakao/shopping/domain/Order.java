package com.kakao.shopping.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @CreatedDate
    private LocalDateTime createdAt;

    protected Order() {
    }

    private Order(UserAccount userAccount) {
        this.userAccount = userAccount;
        this.createdAt = LocalDateTime.now();
    }

    public static Order of(UserAccount userAccount) {
        return new Order(userAccount);
    }
}
