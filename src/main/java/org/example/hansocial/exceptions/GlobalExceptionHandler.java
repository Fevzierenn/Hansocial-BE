package org.example.hansocial.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex
    ) {        ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.getCause());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.getCause());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFound(
        PostNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.getCause());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFound(
        CommentNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.getCause());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFound(
        LikeNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.getCause());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
        HttpRequestMethodNotSupportedException ex
    ) {
        String message = String.format(
            "HTTP method '%s' is not supported for this endpoint. Supported methods are: %s",
            ex.getMethod(),
            ex.getSupportedHttpMethods()
        );

        ErrorResponse error = new ErrorResponse(message);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error); // 405 Method Not Allowed
    }

    //  unexpected (500) errors will catch here.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            "An unexpected error occurred."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            error
        );
    }
}
