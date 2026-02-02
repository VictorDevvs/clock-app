package com.br.app.clock.timer.adapter.out.persistence;

import com.br.app.clock.timer.domain.model.Timer;
import com.br.app.clock.timer.domain.model.TimerStatus;
import com.br.app.clock.timer.domain.model.TimerType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "timers")
public class TimerJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Long initialDurationInSeconds;

    @Column(nullable = false)
    private Long currentTimeInSeconds;

    @Enumerated(EnumType.STRING)
    private TimerStatus timerStatus;

    @Enumerated(EnumType.STRING)
    private TimerType timerType;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static TimerJpaEntity fromDomain(Timer timer) {
        TimerJpaEntity entity = new TimerJpaEntity();
        entity.id = timer.snapshot().id();
        entity.initialDurationInSeconds = timer.snapshot().initialDurationInSeconds();
        entity.currentTimeInSeconds = timer.snapshot().currentTimeInSeconds();
        entity.timerStatus = timer.snapshot().timerStatus();
        entity.timerType = timer.snapshot().timerType();
        entity.createdAt = timer.snapshot().createdAt();
        entity.updatedAt = timer.snapshot().updatedAt();
        return entity;
    }

    public static Timer toDomain(TimerJpaEntity entity) {
        return Timer.restore(
                entity.id,
                entity.initialDurationInSeconds,
                entity.currentTimeInSeconds,
                entity.timerStatus,
                entity.timerType,
                entity.createdAt,
                entity.updatedAt
        );
    }
}
