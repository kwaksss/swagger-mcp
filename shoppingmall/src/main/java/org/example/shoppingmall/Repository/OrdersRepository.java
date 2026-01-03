package org.example.shoppingmall.Repository;

import org.example.shoppingmall.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
}
