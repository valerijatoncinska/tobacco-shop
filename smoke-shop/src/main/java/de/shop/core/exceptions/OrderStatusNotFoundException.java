package de.shop.core.exceptions;

public class OrderStatusNotFoundException extends RuntimeException {
    public OrderStatusNotFoundException(String message) {
        super(message);
    }
    public OrderStatusNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
}
