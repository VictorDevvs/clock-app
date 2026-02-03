package com.br.app.clock.timer.adapter.in.web.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ErrorResponse(
    int status,
    String message,
    String details,
    Instant timestamp
) {
}
