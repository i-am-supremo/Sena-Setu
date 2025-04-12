package com.defense.inventory.dto;

import com.defense.inventory.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class CompanyResponseDto {
    private Long id;
    private Long unitId;
    private String unitName;
    private String name;
    private String description;
}
