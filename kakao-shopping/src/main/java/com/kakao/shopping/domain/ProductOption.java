package com.kakao.shopping.domain;

import com.kakao.shopping.dto.product.option.request.OptionInsertRequest;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "OptionWithProduct",
                attributeNodes = @NamedAttributeNode("product")
        ),
        @NamedEntityGraph(
                name = "OptionWithProductAndCreatedBy",
                attributeNodes = {
                        @NamedAttributeNode("product"),
                        @NamedAttributeNode("createdBy")
                }
        )
})
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

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount createdBy;

    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount modifiedBy;

    protected ProductOption() {
    }

    @Builder
    public ProductOption(Long id, Product product, String name, Long price, Long stock, UserAccount userAccount) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createdAt = LocalDateTime.now();
        this.createdBy = userAccount;
    }

    private ProductOption(Product product, String name, Long price, UserAccount userAccount) {
        this.product = product;
        this.name = name;
        this.price = price;
        this.stock = 10L;
        this.createdAt = LocalDateTime.now();
        this.createdBy = userAccount;
    }

    public static ProductOption of(Product product, String name, Long price, UserAccount userAccount) {
        return new ProductOption(product, name, price, userAccount);
    }

    public static ProductOption of(OptionInsertRequest request, UserAccount userAccount) {
        return new ProductOption(request.product(), request.name(), request.price(), userAccount);
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

    public ProductOption updateProduct(UserAccount userAccount, Product product) {
        this.product = product;
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = userAccount;
        return this;
    }

    public ProductOption updateName(UserAccount userAccount, String name) {
        this.name = name;
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = userAccount;
        return this;
    }

    public ProductOption updatePrice(UserAccount userAccount, Long price) {
        this.price = price;
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = userAccount;
        return this;
    }

    public ProductOption updateStock(UserAccount userAccount, Long stock) {
        this.stock = stock;
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = userAccount;
        return this;
    }
}
