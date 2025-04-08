package com.defense.inventory.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SubProductResponseDto {
    private Long id;
    private String name;
    private String barcode;
    private int quantity;
}
