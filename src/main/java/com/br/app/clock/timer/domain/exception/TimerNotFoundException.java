package com.br.app.clock.timer.domain.exception;

public class TimerNotFoundException extends RuntimeException {
    public TimerNotFoundException(String message) {
        super(message);
    }
}
