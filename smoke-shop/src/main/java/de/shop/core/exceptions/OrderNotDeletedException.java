package de.shop.core.exceptions;

public class OrderNotDeletedException extends RuntimeException {
    public OrderNotDeletedException(String message) {
        super(message);
    }
    public OrderNotDeletedException(String message, Throwable cause) {
        super(message,cause);
    }

}