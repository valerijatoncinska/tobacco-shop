package de.shop.core.exceptions;

public class ProductNotSavedException extends RuntimeException {
    public ProductNotSavedException(String message) {
        super(message);
    }
    public ProductNotSavedException(String message, Throwable cause) {
        super(message,cause);
    }

}