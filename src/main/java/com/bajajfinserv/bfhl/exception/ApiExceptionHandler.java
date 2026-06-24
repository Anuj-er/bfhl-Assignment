package com.bajajfinserv.bfhl.exception;

import com.bajajfinserv.bfhl.config.UserProfileProperties;
import com.bajajfinserv.bfhl.dto.BfhlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    private final UserProfileProperties profile;

    public ApiExceptionHandler(UserProfileProperties profile) {
        this.profile = profile;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BfhlResponse> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Invalid request");
        return badRequest(message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BfhlResponse> handleUnreadableRequest(HttpMessageNotReadableException exception) {
        return badRequest("Request body must be valid JSON with a data array");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BfhlResponse> handleUnexpectedException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BfhlResponse.failure(profile, "An unexpected server error occurred"));
    }

    private ResponseEntity<BfhlResponse> badRequest(String message) {
        return ResponseEntity.badRequest().body(BfhlResponse.failure(profile, message));
    }
}
