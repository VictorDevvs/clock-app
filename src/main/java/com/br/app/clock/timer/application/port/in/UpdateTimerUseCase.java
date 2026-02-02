package com.br.app.clock.timer.application.port.in;

import com.br.app.clock.timer.domain.model.Timer;

public interface UpdateTimerUseCase {

    Timer updateTimer(UpdateTimerCommand command);
}
