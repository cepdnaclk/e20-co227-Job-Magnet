package com.example.jobportal.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "passwordresettokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 500)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryTime;

    @Column(nullable = false)
    private Boolean used = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public PasswordResetToken() {}

    public PasswordResetToken(String email, String token, LocalDateTime expiryTime) {
        this.email = email;
        this.token = token;
        this.expiryTime = expiryTime;
        this.used = false;
        this.createdAt = LocalDateTime.now();
    }
    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", expiryTime=" + expiryTime +
                ", used=" + used +
                ", createdAt=" + createdAt +
                '}';
    }


    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
