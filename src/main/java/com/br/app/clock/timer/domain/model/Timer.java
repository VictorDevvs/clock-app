package com.br.app.clock.timer.domain.model;

import com.br.app.clock.timer.domain.exception.InvalidTimerDurationException;
import com.br.app.clock.timer.domain.exception.InvalidTimerStateException;

import java.time.LocalDateTime;
import java.util.UUID;

public class Timer {

    protected UUID id;
    private Long initialDurationInSeconds;
    private Long currentTimeInSeconds;
    private TimerStatus timerStatus;
    private TimerType timerType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Timer(Long initialDurationInSeconds, TimerType timerType) {
        this.id = UUID.randomUUID();
        if(initialDurationInSeconds < 0){
            throw new InvalidTimerDurationException("Timer duration cannot be negative.");
        }
        this.initialDurationInSeconds = initialDurationInSeconds;
        this.currentTimeInSeconds = (timerType == TimerType.COUNTDOWN) ? initialDurationInSeconds : 0L;
        this.timerType = timerType;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.timerStatus = TimerStatus.STOPPED;
    }

    private Timer(UUID id, Long initialDurationInSeconds, Long currentTimeInSeconds, TimerStatus timerStatus, TimerType timerType,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.initialDurationInSeconds = initialDurationInSeconds;
        this.currentTimeInSeconds = currentTimeInSeconds;
        this.timerStatus = timerStatus;
        this.timerType = timerType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Timer restore(UUID id, Long initialDurationInSeconds, Long currentTimeInSeconds, TimerStatus timerStatus,
                                TimerType timerType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Timer(id, initialDurationInSeconds, currentTimeInSeconds, timerStatus, timerType, createdAt, updatedAt);
    }

    public record Snapshot(UUID id, Long initialDurationInSeconds, Long currentTimeInSeconds, TimerStatus timerStatus,
                           TimerType timerType, LocalDateTime createdAt, LocalDateTime updatedAt ) {}

    public Snapshot snapshot() {
        return new Snapshot(id, initialDurationInSeconds, currentTimeInSeconds, timerStatus, timerType, createdAt,
                updatedAt);
    }

    public void start() {
        if(this.timerStatus == TimerStatus.RUNNING){
            throw new InvalidTimerStateException("Timer is already running.");
        }
        this.timerStatus = TimerStatus.RUNNING;
        this.updatedAt = LocalDateTime.now();
    }

    public void pause() {
        if(this.timerStatus == TimerStatus.PAUSED){
            throw new InvalidTimerStateException("Timer is already paused.");
        }
        this.timerStatus = TimerStatus.PAUSED;
        this.updatedAt = LocalDateTime.now();
    }

    public void stop() {
        if(this.timerStatus == TimerStatus.STOPPED){
            throw new InvalidTimerStateException("Timer is already stopped.");
        }
        this.timerStatus = TimerStatus.STOPPED;
        this.updatedAt = LocalDateTime.now();
    }

    public void tick(){
        if(this.timerStatus == TimerStatus.RUNNING && this.currentTimeInSeconds > 0 && this.timerType == TimerType.COUNTDOWN){
            this.currentTimeInSeconds--;
            this.updatedAt = LocalDateTime.now();
        }

        if(this.timerStatus == TimerStatus.RUNNING && this.timerType == TimerType.STOPWATCH){
            this.currentTimeInSeconds++;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void reset() {
        this.currentTimeInSeconds = this.initialDurationInSeconds;
        this.timerStatus = TimerStatus.STOPPED;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isFinished(){
        return this.timerType == TimerType.COUNTDOWN && this.currentTimeInSeconds == 0;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
