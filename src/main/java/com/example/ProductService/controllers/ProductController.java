package com.example.ProductService.controllers;

import com.example.ProductService.clients.IClientProductDto;
import com.example.ProductService.dtos.ProductDto;
import com.example.ProductService.models.Categories;
import com.example.ProductService.models.Product;
import com.example.ProductService.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    IProductService productService;

    public ProductController(IProductService productService){
        this.productService = productService;
    }
    @GetMapping("")
    public List<Product> getProducts(){

        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public Product getSingleProduct(@PathVariable("productId") long id){
        if(id < 1) {
            throw new IllegalArgumentException("Something went wrong");
        }
        Product product = productService.getSingleProduct(id);
        return product;
    }

    @PostMapping()
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDto productDto){
        System.out.println("try try try");
        Product product = getProduct(productDto);
        Product savedproduct = this.productService.addNewProduct(product);
        ResponseEntity<Product> responseEntity = new ResponseEntity<>(savedproduct, HttpStatus.OK);
        return responseEntity;
        //return productService.addNewProduct(productDto);
    }

    private Product getProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Categories category = new Categories();
        category.setName(productDto.getCategory());
        product.setCategory(category);
        product.setImageUrl(productDto.getImage());
        product.setDescription(productDto.getDescription());
        return product;
    }

    @PutMapping("/{productId}")
    public String updateProduct(@PathVariable("productId") Long id){
        return "updating product";
    }

    @PatchMapping("/{productId}")
    public Product patchProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setCategory(new Categories());
        product.getCategory().setName(productDto.getCategory());
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());

        return this.productService.updateProduct(productId, product);
    }

    @DeleteMapping("/{productId}")  // <-- Updated annotation to capture productId dynamically
    public String deleteProduct(@PathVariable Long productId) {
        // Logic to delete product with given productId
        return "Deleting product with ID: " + productId;
    }
}
