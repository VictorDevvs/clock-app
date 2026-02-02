package com.br.app.clock.timer.application.port.out;

import com.br.app.clock.timer.domain.model.Timer;
import com.br.app.clock.timer.domain.model.TimerStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimerRepository {

    Timer save(Timer timer);
    Optional<Timer> findById(UUID id);
    List<Timer> findAll();
    List<Timer> findByStatus(TimerStatus status);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}
