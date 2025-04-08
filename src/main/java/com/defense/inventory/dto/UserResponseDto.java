package com.defense.inventory.dto;

import com.defense.inventory.entity.enums.Role;
import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String name;
    private Role role;
    private String post;
}
