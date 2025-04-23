package com.defense.inventory.service.impl;

import com.defense.inventory.entity.User;
import com.defense.inventory.exception.InvalidCredentialsException;
import com.defense.inventory.exception.ResourceNotFoundException;
import com.defense.inventory.repository.UserRepository;
import com.defense.inventory.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String changePassword(Long userId, String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            throw new InvalidCredentialsException("Password Change: Current password and New password cannot be same");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user ", "id ", userId));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Old Password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "Password changed successfully";
    }
}
