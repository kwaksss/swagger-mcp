package org.example.shoppingmall.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int price;
    private int stock;

    @Column(name = "created_at")
    private LocalDate createdDate;
}
