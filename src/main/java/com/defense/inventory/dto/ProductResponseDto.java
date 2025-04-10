package com.defense.inventory.dto;

import com.defense.inventory.entity.SubProduct;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponseDto {
    List<SubProduct> subProductList;
    private Long id;
    private String name;
    private String description;
}
