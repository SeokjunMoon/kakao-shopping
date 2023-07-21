package com.kakao.shopping.repository;

import com.kakao.shopping.domain.Option;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    @EntityGraph("OptionWithProduct")
    List<Option> findAll();

    @EntityGraph("OptionWithProduct")
    List<Option> findAllByProductId(Long productId);
}
