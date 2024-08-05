package de.shop.core.exceptions;

public class OrderNotSavedException extends RuntimeException {
    public OrderNotSavedException(String message) {
        super(message);
    }
    public OrderNotSavedException(String message, Throwable cause) {
        super(message,cause);
    }

}