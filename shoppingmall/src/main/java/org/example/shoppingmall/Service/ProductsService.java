package org.example.shoppingmall.Service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Entity.Products;
import org.example.shoppingmall.Repository.ProductsRepository;
import org.example.shoppingmall.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;

    public List<Products> findAll() {
        return productsRepository.findAll();
    }

    public Products save(Products products) {
        return productsRepository.save(products);
    }


}
