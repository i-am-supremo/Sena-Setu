package com.defense.inventory.exception;

public class InvalidRoleException extends RuntimeException {

    String role;

    public InvalidRoleException(String role) {
        super(String.format("%s Role is not eligible to access ", role));
        this.role = role;
    }
}
