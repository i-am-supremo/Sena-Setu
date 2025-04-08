package com.defense.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotBlank(message = "UserName cannot be blank ")
    @Size(min = 3, max = 50, message = "User name must be between 3 and 50 characters ")
    private String name;
    @NotBlank(message = "Password cannot be blank ")
    @Size(min = 3, max = 50, message = "Password must be between 3 and 50 characters ")
    private String password;
}
