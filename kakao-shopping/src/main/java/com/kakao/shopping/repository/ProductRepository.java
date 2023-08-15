package com.kakao.shopping.repository;

import com.kakao.shopping.domain.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph("ProductWithCreatedBy")
    Optional<Product> findById(Long id);
}
