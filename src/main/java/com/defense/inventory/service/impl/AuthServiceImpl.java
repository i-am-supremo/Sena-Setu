package com.defense.inventory.service.impl;

import com.defense.inventory.dto.UserRequestDto;
import com.defense.inventory.dto.UserResponseDto;
import com.defense.inventory.entity.User;
import com.defense.inventory.entity.enums.Role;
import com.defense.inventory.exception.InvalidCredentialsException;
import com.defense.inventory.exception.ResourceAlreadyExistException;
import com.defense.inventory.exception.ResourceNotFoundException;
import com.defense.inventory.repository.UserRepository;
import com.defense.inventory.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthServiceImpl {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtService;

    public UserResponseDto signup(UserRequestDto signupDto) {
        User user = userRepo.findByName(signupDto.getName());
        if (user != null)
            throw new ResourceAlreadyExistException("User already ", "Name ", user.getName());

        User userToBeSaved = modelMapper.map(signupDto, User.class);
        userToBeSaved.setJoinedOn(LocalDate.now());
        userToBeSaved.setRole(Role.USER);
        userToBeSaved.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        return modelMapper.map(userRepo.save(userToBeSaved), UserResponseDto.class);
    }

    public String login(UserRequestDto loginDto) {
        User userCheck = userRepo.findByName(loginDto.getName());
        if (userCheck == null)
            throw new ResourceNotFoundException("No User", "this Name " + loginDto.getName(), 404L);

        if (!passwordEncoder.matches(loginDto.getPassword(), userCheck.getPassword())) {
            throw new InvalidCredentialsException("Password");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword()));

        User user = (User) authentication.getPrincipal();
        return jwtService.generateToken(user);
    }
}
