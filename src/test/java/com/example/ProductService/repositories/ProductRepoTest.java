package com.example.ProductService.repositories;

import com.example.ProductService.models.Categories;
import com.example.ProductService.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductRepoTest {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CateogoryRepo cateogoryRepo;

    @Test
    @Transactional
    void saveProductsAndCategory() {
        Categories categories = new Categories();
        categories.setName("Fashion");
        categories.setDescription("Fashion makes Sense");
        cateogoryRepo.save(categories);

        Product product = new Product();
        product.setTitle("Track Suit");
        product.setDescription("Track Suit long and slim fit");
        product.setCategory(categories);
        productRepo.save(product);
    }

    @Test
    @Transactional
    @Rollback(false)
    void saveProductsAndCategory1(){//fetchmode
        Categories categories = this.cateogoryRepo.findById(1l);
        List<Product> product = categories.getProductList();
        for(Product p : product){
            System.out.println(p.getPrice());
        }
    }
}