package com.br.app.clock.timer.application.port.in;

import com.br.app.clock.timer.domain.model.TimerAction;

import java.util.UUID;

public record UpdateTimerCommand(
        UUID id,
        TimerAction action
) {
}
