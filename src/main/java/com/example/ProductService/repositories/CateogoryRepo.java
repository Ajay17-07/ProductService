package com.example.ProductService.repositories;

import com.example.ProductService.models.Categories;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CateogoryRepo extends JpaRepository<Categories, Long> {
    Categories save(Categories categories);
    Categories findById(long id);
    List<Categories> findByNameEquals(String name, Pageable pageable);;
}
