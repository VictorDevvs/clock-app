package com.br.app.clock.timer.application.port.in;

import com.br.app.clock.timer.domain.model.Timer;

public interface CreateTimerUseCase {

    Timer createTimer(CreateTimerCommand command);
}
