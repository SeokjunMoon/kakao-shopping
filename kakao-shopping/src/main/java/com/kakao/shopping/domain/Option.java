package com.kakao.shopping.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedEntityGraph(
        name = "OptionWithProduct",
        attributeNodes = @NamedAttributeNode("product")
)
@Entity
public class Option {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    private String name;

    private int price;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    protected Option() {
    }

    private Option(Product product, String name, int price) {
        this.product = product;
        this.name = name;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    public static Option of(Product product, String name, int price) {
        return new Option(product, name, price);
    }

    public void setProduct(Product product) {
        this.product = product;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setName(String name) {
        this.name = name;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setPrice(int price) {
        this.price = price;
        this.modifiedAt = LocalDateTime.now();
    }
}
