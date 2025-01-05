package com.example.ProductService.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/categories")
public class CategoryController {


    @GetMapping()
    public String getCategories(){
        return "getting all categories";
    }

    @GetMapping("/{productId}")
    public String getProductsInCategory(@PathVariable("productId") Long id){
        return "get products in category";
    }
}
