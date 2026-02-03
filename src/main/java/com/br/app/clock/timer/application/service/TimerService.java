package com.br.app.clock.timer.application.service;

import com.br.app.clock.timer.application.port.in.*;
import com.br.app.clock.timer.application.port.out.TimerRepository;
import com.br.app.clock.timer.domain.exception.TimerNotFoundException;
import com.br.app.clock.timer.domain.model.Timer;
import com.br.app.clock.timer.domain.model.TimerStatus;
import org.springframework.stereotype.Service;
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
        if(command == null) {
            throw new IllegalArgumentException("CreateTimerCommand cannot be null");
        }
        if(command.timerType() == null){
            throw new IllegalArgumentException("TimerType cannot be null");
        }
        Timer timer = new Timer(
                command.initialDurationInSeconds(),
                command.timerType()
        );

        return timerRepository.save(timer);
    }

    @Override
    public void deleteTimerById(UUID id) {
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
                .orElseThrow(() -> new TimerNotFoundException("Timer not found with id: " + command.id()));
        Timer updatedTimer = applyAction(timer, command);

        return timerRepository.save(updatedTimer);
    }

    private Timer applyAction(Timer timer, UpdateTimerCommand command) {
        return switch (command.action()) {
            case START -> timer.start();
            case PAUSE -> timer.pause();
            case STOP -> timer.stop();
            case RESET -> timer.reset();
            case TICK -> timer.tick();
        };
    }
}
