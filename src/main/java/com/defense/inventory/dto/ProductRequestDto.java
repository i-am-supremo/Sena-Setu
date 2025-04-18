package com.defense.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequestDto {

    @NotBlank(message = "Product Name cannot be blank ")
    @Size(min = 2, max = 50, message = "Product name must be between 2 and 50 characters ")
    private String name;
    @Size(max = 300, message = "Description cannot be more that 300 Chars")
    private String description;
}
