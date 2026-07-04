package org.example.hansocial.exceptions;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;
    private Throwable throwable;
    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    public ErrorResponse(String message, Throwable throwable) {
        this.message = message;
        this.throwable=throwable;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }
    public Throwable getThrowable(){
        return throwable;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}