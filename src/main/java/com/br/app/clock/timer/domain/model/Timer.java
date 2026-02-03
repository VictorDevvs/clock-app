package com.br.app.clock.timer.domain.model;

import com.br.app.clock.timer.domain.exception.InvalidTimerDurationException;
import com.br.app.clock.timer.domain.exception.InvalidTimerStateException;
import com.br.app.clock.timer.domain.exception.TimerAlreadyFinishedException;
import java.time.Instant;
import java.util.UUID;

public class Timer {

    private UUID id;
    private Long initialDurationInSeconds;
    private Long currentTimeInSeconds;
    private TimerStatus timerStatus;
    private TimerType timerType;
    private Instant createdAt;
    private Instant updatedAt;

    public Timer(Long initialDurationInSeconds, TimerType timerType) {
        this.id = UUID.randomUUID();
        if(initialDurationInSeconds < 0){
            throw new InvalidTimerDurationException("Timer duration cannot be negative.");
        }
        if(timerType == TimerType.COUNTDOWN && initialDurationInSeconds == 0){
            throw new InvalidTimerDurationException("Countdown timer must have duration greater than zero.");
        }
        this.initialDurationInSeconds = initialDurationInSeconds;
        this.currentTimeInSeconds = (timerType == TimerType.COUNTDOWN) ? initialDurationInSeconds : 0L;
        this.timerType = timerType;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.timerStatus = TimerStatus.STOPPED;
    }

    private Timer(UUID id, Long initialDurationInSeconds, Long currentTimeInSeconds, TimerStatus timerStatus, TimerType timerType,
                  Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.initialDurationInSeconds = initialDurationInSeconds;
        this.currentTimeInSeconds = currentTimeInSeconds;
        this.timerStatus = timerStatus;
        this.timerType = timerType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Timer restore(UUID id, Long initialDurationInSeconds, Long currentTimeInSeconds, TimerStatus timerStatus,
                                TimerType timerType, Instant createdAt, Instant updatedAt) {
        return new Timer(id, initialDurationInSeconds, currentTimeInSeconds, timerStatus, timerType, createdAt, updatedAt);
    }

    public record Snapshot(UUID id, Long initialDurationInSeconds, Long currentTimeInSeconds, TimerStatus timerStatus,
                           TimerType timerType, Instant createdAt, Instant updatedAt ) {}

    public Snapshot snapshot() {
        return new Snapshot(id, initialDurationInSeconds, currentTimeInSeconds, timerStatus, timerType, createdAt,
                updatedAt);
    }

    public Timer start() {
        if(this.timerStatus == TimerStatus.RUNNING){
            throw new InvalidTimerStateException("Timer is already running.");
        }
        return new Timer(this.id, this.initialDurationInSeconds, this.currentTimeInSeconds,
                TimerStatus.RUNNING, this.timerType, this.createdAt, Instant.now());
    }

    public Timer pause() {
        if(this.timerStatus == TimerStatus.PAUSED){
            throw new InvalidTimerStateException("Timer is already paused.");
        }
        return new Timer(this.id, this.initialDurationInSeconds, this.currentTimeInSeconds,
                TimerStatus.PAUSED, this.timerType, this.createdAt, Instant.now());
    }

    public Timer stop() {
        if(this.timerStatus == TimerStatus.STOPPED){
            throw new InvalidTimerStateException("Timer is already stopped.");
        }
        return new Timer(this.id, this.initialDurationInSeconds, this.currentTimeInSeconds,
                TimerStatus.STOPPED, this.timerType, this.createdAt, Instant.now());
    }

    public Timer tick() {
        if(isFinished()){
            throw new TimerAlreadyFinishedException("Cannot tick a finished countdown timer.");
        }
        if(this.timerStatus != TimerStatus.RUNNING){
            throw new InvalidTimerStateException("Cannot tick a timer that is not running.");
        }

        long newTime = (this.timerType == TimerType.COUNTDOWN)
                ? this.currentTimeInSeconds - 1
                : this.currentTimeInSeconds + 1;

        return new Timer(this.id, this.initialDurationInSeconds, newTime,
                this.timerStatus, this.timerType, this.createdAt, Instant.now());
    }

    public Timer reset() {
        long resetTime = (this.timerType == TimerType.COUNTDOWN)
                ? this.initialDurationInSeconds
                : 0L;

        return new Timer(this.id, this.initialDurationInSeconds, resetTime,
                TimerStatus.STOPPED, this.timerType, this.createdAt, Instant.now());
    }

    public boolean isFinished(){
        return this.timerType == TimerType.COUNTDOWN && this.currentTimeInSeconds == 0;
    }
}
