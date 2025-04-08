package com.defense.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyRequestDto {
    @NotBlank(message = "Company Name cannot be blank ")
    @Size(min = 2, max = 50, message = "Company name must be between 2 and 50 characters ")
    private String name;
    private String description;
}
