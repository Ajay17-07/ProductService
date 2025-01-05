package com.example.ProductService.services;



import com.example.ProductService.clients.IClientProductDto;
import com.example.ProductService.models.Categories;
import com.example.ProductService.models.Product;
import com.example.ProductService.repositories.CateogoryRepo;
import com.example.ProductService.repositories.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public class SelfProductService implements  IProductService{
    ProductRepo productRepo;
    CateogoryRepo categoryRepo;
    public SelfProductService(ProductRepo productRepo, CateogoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }
    @Override
    public List<Product> getAllProducts() {
       // List<Product> products = productRepo.findAll();
        List<Categories> categories = categoryRepo.findAll();
        //Product products1 = productRepo.findByPriceBetween(13.4 , 15.2);
        return null;
        //return null;
    }

    @Override
    public Product getSingleProduct(Long productId) {
        return null;
    }

    @Override
    public Product addNewProduct(IClientProductDto productDto) {
        return null;
    }

    @Override
    public Product addNewProduct(Product product) {
        this.productRepo.save(product);
        Product product1 = this.productRepo.findById(4);
        return product1;
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        return null;
    }

    @Override
    public String deleteProduct(Long productId) {
        return null;
    }
}
