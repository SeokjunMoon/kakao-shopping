package com.kakao.shopping.domain;

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

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

//    @CreatedBy
//    @Column(nullable = false)
//    private Long createdBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

//    @LastModifiedBy
//    @Column(nullable = false)
//    private Long modifiedBy;

    protected Product() {
    }

    private Product(String name, String description, String image, Long price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.starCount = 0L;
        this.createdAt = LocalDateTime.now();
    }

    public static Product of(String name, String description, String image, Long price) {
        return new Product(name, description, image, price);
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

    public void setName(String name) {
        this.name = name;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setImage(String image) {
        this.image = image;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setPrice(Long price) {
        this.price = price;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setStarCount(Long starCount) {
        this.starCount = starCount;
        this.modifiedAt = LocalDateTime.now();
    }
}
