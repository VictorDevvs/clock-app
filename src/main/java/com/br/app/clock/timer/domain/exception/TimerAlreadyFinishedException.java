package com.br.app.clock.timer.domain.exception;

public class TimerAlreadyFinishedException extends RuntimeException {
    public TimerAlreadyFinishedException(String message) {
        super(message);
    }
}
