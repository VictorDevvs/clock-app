package com.br.app.clock.timer.application.service;

import com.br.app.clock.timer.application.port.in.*;
import com.br.app.clock.timer.application.port.out.TimerRepository;
import com.br.app.clock.timer.domain.exception.TimerNotFoundException;
import com.br.app.clock.timer.domain.model.Timer;
import com.br.app.clock.timer.domain.model.TimerStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TimerService implements CreateTimerUseCase, DeleteTimerUseCase, GetTimerUseCase, UpdateTimerUseCase {

    private final TimerRepository timerRepository;

    public TimerService(TimerRepository timerRepository) {
        this.timerRepository = timerRepository;
    }

    @Override
    public Timer createTimer(CreateTimerCommand command) {
        if(command.InitialDurationInSeconds() <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero.");
        }

        Timer timer = new Timer(
                command.InitialDurationInSeconds(),
                command.timerType()
        );

        return timerRepository.save(timer);
    }

    @Override
    public void deleteTimerById(UUID id) {
        Timer timer = timerRepository.findById(id)
                .orElseThrow(() -> new TimerNotFoundException("Timer not found."));
        timerRepository.deleteById(id);
    }

    @Override
    public Optional<Timer> getTimerById(UUID id) {
        return timerRepository.findById(id);
    }

    @Override
    public List<Timer> getAllTimers() {
        return timerRepository.findAll();
    }

    @Override
    public List<Timer> getTimersByStatus(TimerStatus status) {
        return timerRepository.findByStatus(status);
    }

    @Override
    public Timer updateTimer(UpdateTimerCommand command) {
        Timer timer = timerRepository.findById(command.id())
                .orElseThrow(() -> new TimerNotFoundException("Timer not found."));

        switch (command.action()){
            case START -> timer.start();
            case PAUSE -> timer.pause();
            case STOP -> timer.stop();
            case RESET -> timer.reset();
            case TICK -> timer.tick();
        }

        timer.setUpdatedAt(LocalDateTime.now());
        return timerRepository.save(timer);
    }
}
