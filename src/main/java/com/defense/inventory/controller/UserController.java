package com.defense.inventory.controller;

import com.defense.inventory.dto.UserRequestDto;
import com.defense.inventory.dto.UserResponseDto;
import com.defense.inventory.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("Received request to register user: {}", userRequestDto.getName());
        return ResponseEntity.ok(userService.registerUser(userRequestDto));
    }

    @PostMapping("/admin/{name}")
    public ResponseEntity<UserResponseDto> createAdminUser(@PathVariable String name) {
        log.info("Received request to create admin user: {}", name);
        return ResponseEntity.ok(userService.createAdminUser(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        log.info("Received request to get user by id: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        log.info("Received request to get all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("Received request to update user with id: {}", id);
        return ResponseEntity.ok(userService.updateUser(id, userRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        log.info("Received request to delete user with id: {}", id);
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
