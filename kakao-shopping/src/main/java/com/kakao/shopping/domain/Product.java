package com.kakao.shopping.domain;

import com.kakao.shopping.dto.product.request.ProductInsertRequest;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, length = 100)
    private String image;

    @Column(nullable = false)
    private Long price;

    private Long starCount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount createdBy;

    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount modifiedBy;

    protected Product() {
    }

    private Product(String name, String description, String image, Long price, UserAccount userAccount) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.starCount = 0L;
        this.createdAt = LocalDateTime.now();
        this.createdBy = userAccount;
    }

    public static Product of(String name, String description, String image, Long price, UserAccount userAccount) {
        return new Product(name, description, image, price, userAccount);
    }

    public static Product of(ProductInsertRequest request, UserAccount userAccount) {
        return new Product(request.name(), request.description(), request.image(), request.price(), userAccount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Product updateName(UserAccount userAccount, String name) {
        this.name = name;
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = userAccount;
        return this;
    }

    public Product updateDescription(UserAccount userAccount, String description) {
        this.description = description;
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = userAccount;
        return this;
    }

    public Product updateImage(UserAccount userAccount, String image) {
        this.image = image;
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = userAccount;
        return this;
    }

    public Product updatePrice(UserAccount userAccount, Long price) {
        this.price = price;
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = userAccount;
        return this;
    }

    public Product updateStarCount(UserAccount userAccount, Long starCount) {
        this.starCount = starCount;
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = userAccount;
        return this;
    }
}
