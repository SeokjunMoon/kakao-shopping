package com.kakao.shopping.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @CreatedDate
    private LocalDateTime createdAt;

    protected OrderDetail() {
    }

    private OrderDetail(UserAccount userAccount) {
        this.userAccount = userAccount;
        this.createdAt = LocalDateTime.now();
    }

    public static OrderDetail of(UserAccount userAccount) {
        return new OrderDetail(userAccount);
    }
}
