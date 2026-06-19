package com.arka.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTransitionStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransitionStatus(InvalidTransitionStatusException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse("INVALID_STATUS_TRANSITION", ex.getMessage(), LocalDateTime.now()));


    }

    @ExceptionHandler(EmailDeliveryException.class)
    public ResponseEntity<ErrorResponse> handleEmailDeliveryError(EmailDeliveryException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse("ERROR_SENDIND_EMAIL", ex.getMessage(), LocalDateTime.now()));
    }
}
