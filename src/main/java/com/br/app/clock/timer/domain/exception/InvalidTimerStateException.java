package com.br.app.clock.timer.domain.exception;

public class InvalidTimerStateException extends RuntimeException {
    public InvalidTimerStateException(String message) {
        super(message);
    }
}
