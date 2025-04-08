package com.defense.inventory.service;

import com.defense.inventory.dto.UserRequestDto;
import com.defense.inventory.dto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserResponseDto createAdminUser(String name);

    UserResponseDto getUserById(Long userId);

    List<UserResponseDto> getAllUsers();

    UserResponseDto updateUser(Long userId, UserRequestDto updatedUser);

    String deleteUser(Long userId);
}
