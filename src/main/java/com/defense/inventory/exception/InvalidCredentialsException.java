package com.defense.inventory.exception;

public class InvalidCredentialsException extends RuntimeException {

    String field;


    public InvalidCredentialsException(String field) {
        super(String.format("Incorrect %s", field));
        this.field = field;
    }
}
