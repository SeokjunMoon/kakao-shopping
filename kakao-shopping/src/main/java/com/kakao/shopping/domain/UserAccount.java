package com.kakao.shopping.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
public class UserAccount {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

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

    private UserAccount(String name, String email, String password, LocalDate birthdate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.roles = "USER";
        this.createdAt = LocalDateTime.now();
    }

    public static UserAccount of(String name, String email, String password, LocalDate birthdate) {
        return new UserAccount(name, email, password, birthdate);
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
