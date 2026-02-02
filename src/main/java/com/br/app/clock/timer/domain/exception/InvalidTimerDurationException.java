package com.br.app.clock.timer.domain.exception;

public class InvalidTimerDurationException extends RuntimeException {
    public InvalidTimerDurationException(String message) {
        super(message);
    }
}
