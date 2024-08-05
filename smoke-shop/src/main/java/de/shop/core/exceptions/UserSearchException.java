package de.shop.core.exceptions;

public class UserSearchException extends RuntimeException {
    public UserSearchException(String message) {
        super(message);
    }
    public UserSearchException(String message, Throwable cause) {
        super(message,cause);
    }

}
