package de.shop.core.exceptions;

public class ProductAlreadyNotActiveException extends RuntimeException {
    public ProductAlreadyNotActiveException(String message) {
        super(message);
    }
    public ProductAlreadyNotActiveException(String message, Throwable cause) {
        super(message,cause);
    }

}