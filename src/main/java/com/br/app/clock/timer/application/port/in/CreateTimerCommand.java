package com.br.app.clock.timer.application.port.in;

import com.br.app.clock.timer.domain.model.TimerType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateTimerCommand(
        @NotNull(message = "Initial duration must not be null")
        @Min(value = 1, message = "Duration must be at least 1 second")
        Long initialDurationInSeconds,
        @NotNull(message = "Timer type must not be null")
        TimerType timerType
) {
}
