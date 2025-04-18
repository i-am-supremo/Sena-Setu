package com.defense.inventory.controller;

import com.defense.inventory.dto.LoginDto;
import com.defense.inventory.dto.LoginResponseDto;
import com.defense.inventory.dto.UserRequestDto;
import com.defense.inventory.dto.UserResponseDto;
import com.defense.inventory.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Operation(
            summary = "Sign Up User",
            description = "Creates the new user based on name and password"
    )
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto signupDto) {
        UserResponseDto userDto = authService.signup(signupDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Login User and returns bearer token",
            description = "After Success Login it will return Bearer token valid for one hour"
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.ACCEPTED);
    }
}
