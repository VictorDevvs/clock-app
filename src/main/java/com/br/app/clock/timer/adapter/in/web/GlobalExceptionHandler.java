package com.br.app.clock.timer.adapter.in.web;

import com.br.app.clock.timer.adapter.in.web.dto.ErrorResponse;
import com.br.app.clock.timer.domain.exception.InvalidTimerDurationException;
import com.br.app.clock.timer.domain.exception.InvalidTimerStateException;
import com.br.app.clock.timer.domain.exception.TimerAlreadyFinishedException;
import com.br.app.clock.timer.domain.exception.TimerNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTimerDurationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTimerDurationException(InvalidTimerDurationException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(400)
                .message("Invalid timer duration")
                .details(ex.getMessage())
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(TimerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTimerNotFoundException(TimerNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(404)
                .message("Timer not found")
                .details(ex.getMessage())
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(InvalidTimerStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTimerStateException(InvalidTimerStateException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(400)
                .message("Invalid timer state")
                .details(ex.getMessage())
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(TimerAlreadyFinishedException.class)
    public ResponseEntity<ErrorResponse> handleTimerAlreadyFinishedException(TimerAlreadyFinishedException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(400)
                .message("Timer already finished")
                .details(ex.getMessage())
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(400)
                .message("Illegal argument")
                .details(ex.getMessage())
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
