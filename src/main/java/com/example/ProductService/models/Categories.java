package com.example.ProductService.models;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@JsonSerialize
public class Categories extends  BaseModel implements Serializable {
    private String name;
    private String description;
    @OneToMany(mappedBy = "category") //fetch = FetchType.LAZY) // mapped by not applicable for "ManyToOne"
                                                             // available only for oneToMany,ManyToMany and OneToOne
    private List<Product> productList;
}