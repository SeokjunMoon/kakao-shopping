package com.kakao.shopping.repository;

import com.kakao.shopping.domain.ProductOption;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionRepository extends JpaRepository<ProductOption, Long> {
    @EntityGraph("OptionWithProduct")
    List<ProductOption> findAllByProductId(Long productId);

    @EntityGraph("OptionWithProduct")
    List<ProductOption> findAllByIdIn(List<Long> ids);

    @EntityGraph("OptionWithProductAndCreatedBy")
    Optional<ProductOption> findById(Long id);
}
