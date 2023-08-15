package com.kakao.shopping.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "CartWithUserAccountAndOption",
                attributeNodes = {
                        @NamedAttributeNode("userAccount"),
                        @NamedAttributeNode("productOption")
                }
        ),
        @NamedEntityGraph(
                name = "CartWithUserAccountAndOptionAndProduct",
                attributeNodes = {
                        @NamedAttributeNode("userAccount"),
                        @NamedAttributeNode(value = "productOption", subgraph = "optionSubgraph"),
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "optionSubgraph",
                                attributeNodes = @NamedAttributeNode("product")
                        )
                }
        )
})
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductOption productOption;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long price;

    protected Cart() {
    }

    @Builder
    public Cart(Long id, UserAccount userAccount, ProductOption productOption, Long quantity) {
        this.id = id;
        this.userAccount = userAccount;
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = productOption.getPrice() * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart cart)) return false;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void updateQuantity(Long quantity) {
        this.quantity = quantity;
        this.price = quantity * this.productOption.getPrice();
    }
}
