package de.shop.core.exceptions;

public class AccessException extends RuntimeException {
    public AccessException(String message) {
        super(message);
    }
    public AccessException(String message, Throwable cause) {
        super(message,cause);
    }
}
