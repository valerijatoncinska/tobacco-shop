package de.shop.core.exceptions;

public class RefreshTokenException extends RuntimeException {
    public RefreshTokenException(String message) {
        super(message);
    }
    public RefreshTokenException(String message, Throwable cause) {
        super(message,cause);
    }

}
