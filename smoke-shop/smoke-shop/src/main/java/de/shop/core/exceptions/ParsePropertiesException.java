package de.shop.core.exceptions;

/**
 * Класс ообработки ошибок для класса ParseProperties
 */
public class ParsePropertiesException extends RuntimeException {
    public ParsePropertiesException(String message) {
        super(message);
    }

    public ParsePropertiesException(String message, Throwable cause) {
        super(message, cause);
    }
}
