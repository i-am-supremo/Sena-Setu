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
    @NotBlank(message = "First Name cannot be blank ")
    @Size(min = 3, max = 50, message = "First Name must be between 3 and 50 characters ")
    private String firstName;
    private String lastName;
    @NotBlank(message = "Army Number cannot be blank ")
    private String armyNumber;
    @NotBlank(message = "Company cannot be blank ")
    private String company;
    @NotBlank(message = "Rank cannot be blank ")
    @Size(min = 2, max = 50, message = "Rank must be between 3 and 50 characters ")
    private String rank;
}
