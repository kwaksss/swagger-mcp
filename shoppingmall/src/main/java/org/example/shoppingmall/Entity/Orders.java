package org.example.shoppingmall.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "user_id")
    private int userId;
    @Column(name = "product_id")
    private int productId;

    private int quantity;
    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "ordered_date")
    private LocalDate orderDate;
}
