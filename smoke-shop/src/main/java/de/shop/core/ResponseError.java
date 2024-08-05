package de.shop.core;

public class ResponseError {
    private String message;
    public ResponseError(String message) {
        this.message = message;
    }
    public ResponseError() {

    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
