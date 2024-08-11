package de.shop.core.exceptions;

public class AddressNotfoundException extends RuntimeException {
    public AddressNotfoundException(String message) {
        super(message);
    }
    public AddressNotfoundException(String message, Throwable cause) {
        super(message,cause);
    }
}
