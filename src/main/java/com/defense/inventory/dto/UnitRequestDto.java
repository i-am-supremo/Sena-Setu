package com.defense.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UnitRequestDto {

    @NotBlank(message = "Unit Name cannot be blank ")
    @Size(min = 1, max = 25, message = "Unit name must be between 1 and 25 characters ")
    private String name;
    private String description;
}
