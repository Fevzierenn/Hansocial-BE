package org.example.hansocial.exceptions;

public class LikeNotFoundException extends RuntimeException {
    public LikeNotFoundException() {
        super("Like not found");
    }
    public LikeNotFoundException(String message) {
        super(message);
    }
    public LikeNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }

}
