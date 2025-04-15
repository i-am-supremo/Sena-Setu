package com.defense.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubProductRequestDto {

    @NotBlank(message = "Sub Product cannot be blank ")
    @Size(min = 2, max = 50, message = "Sub Product name must be between 2 and 50 characters ")
    private String name;
    private int quantity;
}
