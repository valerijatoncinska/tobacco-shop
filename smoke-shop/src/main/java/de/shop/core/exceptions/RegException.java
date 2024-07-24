package de.shop.core.exceptions;

public class RegException extends RuntimeException {
    public RegException(String message) {
        super(message);
    }
    public RegException(String message, Throwable cause) {
        super(message,cause);
    }

}
