package com.defense.inventory.service;

public interface AccountService {
    String changePassword(Long id, String oldPassword, String newPassword);
}
