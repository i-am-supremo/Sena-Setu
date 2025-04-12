package com.defense.inventory.dto;

import lombok.Data;

@Data
public class CompanyResponseDto {
    private Long id;
    private Long unitId;
    private String unitName;
    private String name;
    private String description;
}
