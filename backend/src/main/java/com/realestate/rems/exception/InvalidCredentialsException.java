package com.realestate.rems.exception;

/**
 * Exception thrown when authentication credentials are invalid.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}

