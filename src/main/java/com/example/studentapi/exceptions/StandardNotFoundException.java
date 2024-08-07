package com.example.studentapi.exceptions;

public class StandardNotFoundException extends RuntimeException {
    public StandardNotFoundException(String message) {
        super(message);
    }
}
