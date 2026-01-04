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

    //상품 목록 조회
    public List<Products> findAll() {

        return productsRepository.findAll();
    }

    //상품 등록.
    public Products save(Products products) {

        return productsRepository.save(products);
    }


}
