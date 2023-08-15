package com.kakao.shopping.repository;

import com.kakao.shopping.domain.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @EntityGraph("CartWithUserAccountAndOptionAndProduct")
    Optional<List<Cart>> findAllByUserAccountId(Long userId);
}
