package com.defense.inventory.dto;

import com.defense.inventory.entity.Company;
import lombok.Data;

import java.util.List;

@Data
public class BarcodeResponseDto {
    private Long id;
    private String name;
    private String description;
    private List<Company> companyList;
}
