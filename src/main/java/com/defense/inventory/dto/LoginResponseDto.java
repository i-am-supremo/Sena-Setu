package com.defense.inventory.dto;

import com.defense.inventory.entity.enums.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginResponseDto {

    Long id;
    String name;
    Role role;
    String post;
    LocalDate joinedOn;
    String token;

}
