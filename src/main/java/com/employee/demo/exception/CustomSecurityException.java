package com.employee.demo.exception;

public class CustomSecurityException extends RuntimeException {
    public CustomSecurityException(String message) {
        super(message);
    }
}
