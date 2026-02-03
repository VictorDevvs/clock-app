package com.br.app.clock.timer.adapter.in.web.dto;

import com.br.app.clock.timer.domain.model.Timer;
import com.br.app.clock.timer.domain.model.TimerStatus;
import com.br.app.clock.timer.domain.model.TimerType;

import java.time.Instant;
import java.util.UUID;

public record TimerResponse (
        UUID id,
        Long initialDurationInSeconds,
        Long currentTimeInSeconds,
        TimerStatus status,
        TimerType type,
        Instant createdAt,
        Instant updatedAt
){
    public static TimerResponse fromDomain(Timer timer) {
        var snapshot = timer.snapshot();
        return new TimerResponse(
                snapshot.id(),
                snapshot.initialDurationInSeconds(),
                snapshot.currentTimeInSeconds(),
                snapshot.timerStatus(),
                snapshot.timerType(),
                snapshot.createdAt(),
                snapshot.updatedAt()
        );
    }
}
