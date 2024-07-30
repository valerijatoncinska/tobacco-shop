package de.shop.core.exceptions;

public class OrderNotDeleted extends RuntimeException {
    public OrderNotDeleted(String message) {
        super(message);
    }
    public OrderNotDeleted(String message, Throwable cause) {
        super(message,cause);
    }

}