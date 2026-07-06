package org.example.hansocial.exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
            super("Comment not found.");
    }
    public CommentNotFoundException(String message) {
        super(message);
    }

    public CommentNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
}
