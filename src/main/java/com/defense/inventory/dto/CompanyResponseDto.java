package com.defense.inventory.dto;

import com.defense.inventory.entity.Product;
import com.defense.inventory.entity.Unit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class CompanyResponseDto {
    private Long id;
    private String name;
    private String description;
    private Unit unit;
    private List<Product> productList;
}
