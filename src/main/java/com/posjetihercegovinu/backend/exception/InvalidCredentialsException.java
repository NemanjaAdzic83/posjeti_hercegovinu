package com.posjetihercegovinu.backend.exception;

// Bacamo exception kad se email ili lozinka pogresni pri prijavi (-> 401)
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
