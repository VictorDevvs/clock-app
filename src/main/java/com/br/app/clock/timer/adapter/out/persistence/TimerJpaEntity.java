package com.br.app.clock.timer.adapter.out.persistence;

import com.br.app.clock.timer.domain.model.Timer;
import com.br.app.clock.timer.domain.model.TimerStatus;
import com.br.app.clock.timer.domain.model.TimerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "timers")
@Getter
@Setter
@NoArgsConstructor
public class TimerJpaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @Column(nullable = false)
    private Long initialDurationInSeconds;

    @Column(nullable = false)
    private Long currentTimeInSeconds;

    @Enumerated(EnumType.STRING)
    private TimerStatus status;

    @Enumerated(EnumType.STRING)
    private TimerType timerType;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    public static TimerJpaEntity fromDomain(Timer timer) {
        TimerJpaEntity entity = new TimerJpaEntity();
        entity.id = timer.snapshot().id();
        entity.initialDurationInSeconds = timer.snapshot().initialDurationInSeconds();
        entity.currentTimeInSeconds = timer.snapshot().currentTimeInSeconds();
        entity.status = timer.snapshot().timerStatus();
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
                entity.status,
                entity.timerType,
                entity.createdAt,
                entity.updatedAt
        );
    }

    public void updateFromDomain(Timer timer) {
        Timer.Snapshot snapshot = timer.snapshot();
        this.id = snapshot.id();
        this.initialDurationInSeconds = snapshot.initialDurationInSeconds();
        this.currentTimeInSeconds = snapshot.currentTimeInSeconds();
        this.status = snapshot.timerStatus();
        this.timerType = snapshot.timerType();
        this.createdAt = snapshot.createdAt();
        this.updatedAt = snapshot.updatedAt();
    }
}
