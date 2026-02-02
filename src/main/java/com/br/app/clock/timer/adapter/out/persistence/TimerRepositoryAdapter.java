package com.br.app.clock.timer.adapter.out.persistence;

import com.br.app.clock.timer.application.port.out.TimerRepository;
import com.br.app.clock.timer.domain.model.Timer;
import com.br.app.clock.timer.domain.model.TimerStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TimerRepositoryAdapter implements TimerRepository {

    private final SpringDataTimerRepository springDataTimerRepository;

    public TimerRepositoryAdapter(SpringDataTimerRepository springDataTimerRepository) {
        this.springDataTimerRepository = springDataTimerRepository;
    }

    @Override
    public Timer save(Timer timer) {
        TimerJpaEntity entity = springDataTimerRepository.save(TimerJpaEntity.fromDomain(timer));
        return TimerJpaEntity.toDomain(entity);
    }

    @Override
    public Optional<Timer> findById(UUID id) {
        return springDataTimerRepository.findById(id)
                .map(TimerJpaEntity::toDomain);
    }

    @Override
    public List<Timer> findAll() {
        return springDataTimerRepository.findAll().stream().map(
                TimerJpaEntity::toDomain
        ).toList();
    }

    @Override
    public List<Timer> findByStatus(TimerStatus status) {
        return springDataTimerRepository.findByStatus(status)
                .stream()
                .map(TimerJpaEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        springDataTimerRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return springDataTimerRepository.existsById(id);
    }
}
