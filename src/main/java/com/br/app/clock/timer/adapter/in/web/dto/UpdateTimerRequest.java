package com.br.app.clock.timer.adapter.in.web.dto;

import com.br.app.clock.timer.domain.model.TimerAction;
import jakarta.validation.constraints.NotNull;

public record UpdateTimerRequest(
        @NotNull(message = "Timer action must not be null")
        TimerAction action
) {
}
