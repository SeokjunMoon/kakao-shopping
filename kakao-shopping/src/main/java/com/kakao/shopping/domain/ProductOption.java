package com.kakao.shopping.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NamedEntityGraph(
        name = "OptionWithProduct",
        attributeNodes = @NamedAttributeNode("product")
)
@Entity
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long stock;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    protected ProductOption() {
    }

    @Builder
    public ProductOption(Long id, Product product, String name, Long price, Long stock) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createdAt = LocalDateTime.now();
    }

    private ProductOption(Product product, String name, Long price) {
        this.product = product;
        this.name = name;
        this.price = price;
        this.stock = 10L;
        this.createdAt = LocalDateTime.now();
    }

    public static ProductOption of(Product product, String name, Long price) {
        return new ProductOption(product, name, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductOption that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void updateProduct(Product product) {
        this.product = product;
        this.modifiedAt = LocalDateTime.now();
    }

    public void updateName(String name) {
        this.name = name;
        this.modifiedAt = LocalDateTime.now();
    }

    public void updatePrice(Long price) {
        this.price = price;
        this.modifiedAt = LocalDateTime.now();
    }

    public void updateStock(Long stock) {
        this.stock = stock;
        this.modifiedAt = LocalDateTime.now();
    }
}
