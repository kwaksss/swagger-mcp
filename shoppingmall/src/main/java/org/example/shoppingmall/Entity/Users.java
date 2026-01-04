package org.example.shoppingmall.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String password;

    @Column(name = "created_at",insertable = false,updatable = false)
    private LocalDateTime createdAt;
}
