package de.shop.core.exceptions;

public class RegConflictException extends RuntimeException {
    public RegConflictException(String message) {
        super(message);
    }
    public RegConflictException(String message, Throwable cause) {
        super(message,cause);
    }

}
