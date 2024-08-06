package de.shop.core.exceptions;

public class CartNotfoundException extends RuntimeException {
    public CartNotfoundException(String message) {
        super(message);
    }
    public CartNotfoundException(String message, Throwable cause) {
        super(message,cause);
    }
}
