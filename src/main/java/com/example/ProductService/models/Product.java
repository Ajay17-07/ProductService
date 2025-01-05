package com.example.ProductService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@JsonSerialize
public class Product extends BaseModel implements Serializable {
    private String title;
    private double price;
    private String description;
    private String imageUrl;
    @ManyToOne(cascade = CascadeType.ALL) // mapped by not applicable for "ManyToOne"
                                        // available only for oneToMany,ManyToMany and OneToOne
    private Categories category;
    private int testVariable;
}
