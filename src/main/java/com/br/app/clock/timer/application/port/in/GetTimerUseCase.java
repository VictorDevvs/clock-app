package com.br.app.clock.timer.application.port.in;

import com.br.app.clock.timer.domain.model.Timer;
import com.br.app.clock.timer.domain.model.TimerStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetTimerUseCase {
    Optional<Timer> getTimerById(UUID id);

    List<Timer> getAllTimers();

    List<Timer> getTimersByStatus(TimerStatus status);
}
