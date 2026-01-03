package org.example.shoppingmall.Controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Entity.Products;
import org.example.shoppingmall.Service.ProductsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//반환값은 JSON으로 자동 변환
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;

    @GetMapping//data를 가져와
    public List<Products> getProducts() {
        return productsService.findAll();
    }

    @PostMapping //데이터를 전송
    public Products createProduct(@RequestBody Products products) {
        return productsService.save(products);
    }
}
