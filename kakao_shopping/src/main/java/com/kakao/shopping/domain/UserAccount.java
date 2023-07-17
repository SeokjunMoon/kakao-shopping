package com.kakao.shopping.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    private UserAccount(String name, String email, String password, LocalDate birthdate, String roles, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.roles = roles;
        this.createdAt = createdAt;
    }

    public static UserAccount of(String name, String email, String password, LocalDate birthdate, String roles, LocalDateTime createdAt) {
        return new UserAccount(name, email, password, birthdate, roles, createdAt);
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

    public void setName(String name) {
        this.name = name;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setEmail(String email) {
        this.email = email;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setPassword(String password) {
        this.password = password;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setRoles(String roles) {
        this.roles = roles;
        this.modifiedAt = LocalDateTime.now();
    }
}
