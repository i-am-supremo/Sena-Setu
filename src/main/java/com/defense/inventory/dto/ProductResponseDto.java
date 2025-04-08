package com.defense.inventory.dto;

import com.defense.inventory.entity.Company;
import com.defense.inventory.entity.SubProduct;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    List<SubProduct> subProductList;
}
