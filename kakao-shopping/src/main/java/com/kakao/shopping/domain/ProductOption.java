package com.kakao.shopping.domain;

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
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    protected ProductOption() {
    }

    private ProductOption(Product product, String name, Long price) {
        this.product = product;
        this.name = name;
        this.price = price;
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

    public void setProduct(Product product) {
        this.product = product;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setName(String name) {
        this.name = name;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setPrice(Long price) {
        this.price = price;
        this.modifiedAt = LocalDateTime.now();
    }
}
