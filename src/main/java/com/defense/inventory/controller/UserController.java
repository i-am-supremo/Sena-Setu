package com.defense.inventory.controller;

import com.defense.inventory.dto.UpdateUserRequestDto;
import com.defense.inventory.dto.UserResponseDto;
import com.defense.inventory.service.AccountService;
import com.defense.inventory.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    @Operation(
            summary = "Update User to Admin, ADMIN Access Only",
            description = "Updates the User to Admin"
    )
    @PostMapping("/admin/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> createAdminUser(@PathVariable String name) {
        log.info("Received request to create admin user: {}", name);
        return ResponseEntity.ok(userService.createAdminUser(name));
    }

    @Operation(
            summary = "Gets User Details By Id",
            description = "Return user details by id"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        log.info("Received request to get user by id: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = "Return List of all Users ADMIN Access Only",
            description = "Return all user details"
    )
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        log.info("Received request to get all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            summary = "Update the Detail of User",
            description = "Updates user details"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequestDto userRequestDto) {
        log.info("Received request to update user with id: {}", id);
        return ResponseEntity.ok(userService.updateUser(id, userRequestDto));
    }

    @Operation(
            summary = "Delete the Detail of User, ADMIN Access Only",
            description = "Delete user details"
    )
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        log.info("Received request to delete user with id: {}", id);
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @Operation(summary = "Change user password", description = "Allows user to change their password")
    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestParam Long userId,
                                                              @RequestParam String oldPassword,
                                                              @RequestParam String newPassword) {
        String responseMessage = accountService.changePassword(userId, oldPassword, newPassword);
        Map<String, String> response = new HashMap<>();
        response.put("message", responseMessage);

        return ResponseEntity.ok(response);
    }

}
