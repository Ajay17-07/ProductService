package com.example.ProductService.services;

import com.example.ProductService.models.Categories;
import com.example.ProductService.models.Product;
import com.example.ProductService.models.SortParam;
import com.example.ProductService.repositories.CateogoryRepo;
import com.example.ProductService.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private ProductRepo productRepo;
    private CateogoryRepo cateogoryRepo;

    @Autowired
    public SearchService(ProductRepo productRepo, CateogoryRepo cateogoryRepo) {
        this.productRepo = productRepo;
        this.cateogoryRepo = cateogoryRepo;
    }

    public List<Product> searchProducts(String query) {
        return productRepo.findByTitleEquals(query);
    }

    public List<Categories> searchCategories(String query, int pageNumber, int sizeOfPage, List<SortParam> sortParamList) {
//        Sort sort = Sort.by("name").descending()
//                 .and(Sort.by("id").descending());
        Sort sort;
        if(sortParamList.get(0).getSortType().equals("ASC")){
            sort = Sort.by(sortParamList.get(0).getParamName());
        }else{
            sort = Sort.by(sortParamList.get(0).getParamName()).descending();
        }

        for(int i = 1; i < sortParamList.size(); i++){
            if(sortParamList.get(i).getSortType().equals("ASC")){
                sort = sort.and(Sort.by(sortParamList.get(i).getParamName()).ascending());
            }else{
                sort = sort.and(Sort.by(sortParamList.get(i).getParamName()).descending());
            }
        }
        List<Categories> result =  cateogoryRepo.findByNameEquals(query, PageRequest.of(pageNumber, sizeOfPage, sort));
        List<Categories> categories = cateogoryRepo.findAll();
        return result;
    }
}
