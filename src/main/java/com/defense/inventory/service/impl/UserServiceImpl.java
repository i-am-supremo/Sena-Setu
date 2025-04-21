package com.defense.inventory.service.impl;

import com.defense.inventory.dto.UserRequestDto;
import com.defense.inventory.dto.UserResponseDto;
import com.defense.inventory.entity.User;
import com.defense.inventory.entity.enums.Role;
import com.defense.inventory.exception.InvalidCredentialsException;
import com.defense.inventory.exception.ResourceNotFoundException;
import com.defense.inventory.repository.UserRepository;
import com.defense.inventory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createAdminUser(String name) {
        User user = userRepository.findByName(name);
        if (user == null)
            throw new ResourceNotFoundException("No user ", "Name " + name + " ", 404L);
        user.setRole(Role.ADMIN);
        log.info("Setting user as Admin...");
        return modelMapper.map(userRepository.save(user), UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        log.info("Getting user details by Id");
        return modelMapper.map(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user ", "id ", userId)), UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        log.info("Getting all user details");
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .toList();
    }


    @Override
    public UserResponseDto updateUser(Long userId, UserRequestDto updatedUser) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user ", "id ", userId));
        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());
        log.info("Updating user details of {}", user.getName());
        return modelMapper.map(userRepository.save(user), UserResponseDto.class);
    }

    @Override
    @Transactional
    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user ", "id ", userId));
        log.info("Deleting user {}", user.getName());
        userRepository.delete(user);
        return "User Deleted Successfully";
    }

    @Override
    public String changePassword(Long userId, String currentPassword, String newPassword) {
        if (currentPassword.equals(newPassword)) {
            throw new InvalidCredentialsException("Password Change: Current password and New password cannot be same");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user ", "id ", userId));
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Old Password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "Password Updated SuccessFully";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null)
            throw new ResourceNotFoundException("No User ", "Name", 404L);
        return user;
    }

    public User getUserByIdJWT(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user", "id", userId));
    }
}
