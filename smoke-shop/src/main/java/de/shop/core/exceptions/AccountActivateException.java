package de.shop.core.exceptions;

public class AccountActivateException extends RuntimeException {
    public AccountActivateException(String message) {
        super(message);
    }
    public AccountActivateException(String message, Throwable cause) {
        super(message,cause);
    }
}
