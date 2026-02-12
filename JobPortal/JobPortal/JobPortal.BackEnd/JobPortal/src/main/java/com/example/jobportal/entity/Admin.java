package com.example.jobportal.entity;

import jakarta.persistence.*;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates ID values
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true) // Email should be unique and not null
    private String email;

    @Column(nullable = false) // Password should not be null
    private String password;

    // Getters and setters for the fields
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
