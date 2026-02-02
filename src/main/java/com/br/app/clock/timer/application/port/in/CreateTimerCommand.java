package com.br.app.clock.timer.application.port.in;

import com.br.app.clock.timer.domain.model.TimerType;

public record CreateTimerCommand(
        Long InitialDurationInSeconds,
        TimerType timerType
) {
}
