package de.shop.core.exceptions;

public class CartConflictException extends RuntimeException {
    public CartConflictException(String message) {
        super(message);
    }
    public CartConflictException(String message, Throwable cause) {
        super(message,cause);
    }
}
