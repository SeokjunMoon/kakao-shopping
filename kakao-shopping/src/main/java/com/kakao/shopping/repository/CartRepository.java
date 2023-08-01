package com.kakao.shopping.repository;

import com.kakao.shopping.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select c " +
            "from Cart c " +
            "join fetch c.userAccount " +
            "join fetch c.productOption o " +
            "join fetch o.product " +
            "where c.userAccount.id = :userId " +
            "order by c.productOption.id asc")
    Optional<List<Cart>> findByUserIdOrderByOptionIdAsc(Long userId);
}
