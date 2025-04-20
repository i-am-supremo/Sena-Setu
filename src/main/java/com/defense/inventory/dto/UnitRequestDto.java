package com.defense.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UnitRequestDto {

    @NotBlank(message = "Unit Name cannot be blank ")
    @Size(min = 1, max = 50, message = "Unit name must be between 1 and 50 characters ")
    private String name;
    @Size(max = 300, message = "Description cannot be more that 300 Chars")
    private String description;
}
