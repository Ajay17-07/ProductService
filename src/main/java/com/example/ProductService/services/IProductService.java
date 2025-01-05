package com.example.ProductService.services;


import java.util.List;

import com.example.ProductService.clients.IClientProductDto;
import com.example.ProductService.models.Product;
public interface IProductService {

    List<Product> getAllProducts();

    Product getSingleProduct(Long productId);

    public Product addNewProduct(IClientProductDto productDto);

    Product addNewProduct(Product productDto);

    Product updateProduct(Long productId, Product product);

    String deleteProduct(Long productId);
}
