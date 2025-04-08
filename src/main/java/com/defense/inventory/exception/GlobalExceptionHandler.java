package com.defense.inventory.exception;

import com.defense.inventory.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> resourceNotFoundHandler(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        ExceptionDto exceptionDto = new ExceptionDto(message, false);
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ExceptionDto> resourceFoundHandler(ResourceAlreadyExistException ex) {
        String message = ex.getMessage();
        ExceptionDto exceptionDto = new ExceptionDto(message, false);
        return new ResponseEntity<>(exceptionDto, HttpStatus.FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String errorMessages = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ExceptionDto exceptionDto = new ExceptionDto(errorMessages, false);
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }
}
