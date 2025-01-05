package com.example.ProductService.repositories;

import com.example.ProductService.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ProductRepo extends JpaRepository<Product, Long> {
    Product save(Product product);
    Product findById(long id);
    Product findByPriceBetween(double minPrice, double maxPrice);
    List<Product> findByTitleEquals(String title);
}
