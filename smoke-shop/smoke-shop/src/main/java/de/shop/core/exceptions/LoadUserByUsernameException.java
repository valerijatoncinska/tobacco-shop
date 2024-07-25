package de.shop.core.exceptions;

public class LoadUserByUsernameException extends RuntimeException {
    public LoadUserByUsernameException(String message) {
        super(message);
    }
    public LoadUserByUsernameException(String message,Throwable cause) {
        super(message,cause);
    }

}
