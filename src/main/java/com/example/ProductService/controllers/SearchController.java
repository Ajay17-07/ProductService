package com.example.ProductService.controllers;

import com.example.ProductService.dtos.ProductDto;
import com.example.ProductService.dtos.SearchRequestDto;
import com.example.ProductService.models.Categories;
import com.example.ProductService.models.Product;
import com.example.ProductService.services.SearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public List<ProductDto> searchProducts(@RequestBody SearchRequestDto searchRequestDto){
        List<Product> result = searchService.searchProducts(searchRequestDto.getQuery());
        List<ProductDto> shareableResult = new LinkedList<>();
        for (Product product : result) {
            shareableResult.add(getProduct(product));
        }
        return shareableResult;
    }

    @PostMapping("/categories")
    public List<Categories> searchCategories(@RequestBody SearchRequestDto searchRequestDto){
        List<Categories> result = searchService.searchCategories(searchRequestDto.getQuery(),
                searchRequestDto.getPageNumber(), searchRequestDto.getSizeOfPage(),searchRequestDto.getSortParamList());
        return result;
    }
    private ProductDto getProduct(Product p) {
        ProductDto product = new ProductDto();
        product.setId(p.getId());
        product.setTitle(p.getTitle());
        product.setPrice(p.getPrice());
        return product;
    }
}
