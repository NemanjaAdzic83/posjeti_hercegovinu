package com.posjetihercegovinu.backend.exception;

// Bacamo exception kad email vec postoji pri registraciji ( -> 409)
public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
