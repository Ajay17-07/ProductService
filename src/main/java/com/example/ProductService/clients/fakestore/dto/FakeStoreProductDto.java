package com.example.ProductService.clients.fakestore.dto;

import com.example.ProductService.clients.IClientProductDto;
import com.example.ProductService.dtos.RatingDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FakeStoreProductDto implements IClientProductDto {
    private long id;
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;
    private RatingDto rating;
}

