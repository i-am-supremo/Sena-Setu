package com.defense.inventory.dto;

import com.defense.inventory.entity.enums.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginResponseDto {

    private Long id;
    private String name;
    private String firstName;
    private String lastName;
    private String armyNumber;
    private String company;
    private Role role;
    private String rank;
    private LocalDate joinedOn;
    private String token;

}
