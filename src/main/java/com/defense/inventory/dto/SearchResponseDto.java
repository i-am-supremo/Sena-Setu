package com.defense.inventory.dto;

import com.defense.inventory.entity.enums.Type;
import lombok.Data;

@Data
public class SearchResponseDto {

    private Long id;
    private String name;
    private Type type;
    private String productName;
    private String companyName;
    private String unitName;
    private int quantity;
    private String barcode;
}
