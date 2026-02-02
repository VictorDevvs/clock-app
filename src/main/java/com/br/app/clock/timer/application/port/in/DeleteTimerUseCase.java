package com.br.app.clock.timer.application.port.in;

import java.util.UUID;

public interface DeleteTimerUseCase {

    void deleteTimerById(UUID id);
}
