package com.kakao.shopping.repository;

import com.kakao.shopping.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
