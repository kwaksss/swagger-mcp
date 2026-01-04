package org.example.shoppingmall.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="products")
@Getter
@Setter //지양해야된다고 함.

public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int price;
    private int stock;

    @Column(name = "created_at",insertable = false,updatable = false)
    private LocalDateTime createdDate;
}
