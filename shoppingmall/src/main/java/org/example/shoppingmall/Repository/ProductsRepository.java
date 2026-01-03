package org.example.shoppingmall.Repository;

import org.example.shoppingmall.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products,Integer> {
}
