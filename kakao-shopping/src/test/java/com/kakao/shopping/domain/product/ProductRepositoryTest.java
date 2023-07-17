package com.kakao.shopping.domain.product;

import com.kakao.shopping.domain.Product;
import com.kakao.shopping.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Product Repository Test")
@SpringBootTest
public class ProductRepositoryTest {
    private final ProductRepository productRepository;

    public ProductRepositoryTest(
            @Autowired ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }
}
