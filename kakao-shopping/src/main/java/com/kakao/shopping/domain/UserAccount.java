package com.kakao.shopping.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 256)
    private String password;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, length = 30)
    private String roles;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    protected UserAccount() {
    }

    @Builder
    public UserAccount(Long id, String name, String provider, String email, String password, LocalDate birthdate, String roles) {
        this.id = id;
        this.name = name;
        this.provider = (provider == null? "EMAIL" : provider);
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.roles = (roles == null? "USER" : roles);
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void updateName(String name) {
        this.name = name;
        this.modifiedAt = LocalDateTime.now();
    }

    public void updateEmail(String email) {
        this.email = email;
        this.modifiedAt = LocalDateTime.now();
    }

    public void updatePassword(String password) {
        this.password = password;
        this.modifiedAt = LocalDateTime.now();
    }

    public void updateRoles(String roles) {
        this.roles = roles;
        this.modifiedAt = LocalDateTime.now();
    }

    // 비밀번호가 그대로 DB에 보관되지 않도록 암호화 하기 위한 메서드
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
