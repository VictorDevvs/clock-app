package com.br.app.clock.timer.adapter.in.web;

import com.br.app.clock.timer.adapter.in.web.dto.TimerResponse;
import com.br.app.clock.timer.adapter.in.web.dto.UpdateTimerRequest;
import com.br.app.clock.timer.application.port.in.*;
import com.br.app.clock.timer.domain.model.Timer;
import com.br.app.clock.timer.domain.model.TimerStatus;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/timers")
@Validated
public class TimerController {

    private final CreateTimerUseCase createTimerUseCase;
    private final UpdateTimerUseCase updateTimerUseCase;
    private final GetTimerUseCase getTimerUseCase;
    private final DeleteTimerUseCase deleteTimerUseCase;

    public TimerController(CreateTimerUseCase createTimerUseCase,
                           UpdateTimerUseCase updateTimerUseCase,
                           GetTimerUseCase getTimerUseCase,
                           DeleteTimerUseCase deleteTimerUseCase) {
        this.createTimerUseCase = createTimerUseCase;
        this.updateTimerUseCase = updateTimerUseCase;
        this.getTimerUseCase = getTimerUseCase;
        this.deleteTimerUseCase = deleteTimerUseCase;
    }

    @PostMapping
    public ResponseEntity<TimerResponse> createTimer(@RequestBody @Valid CreateTimerCommand command) {
        Timer timer = createTimerUseCase.createTimer(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(TimerResponse.fromDomain(timer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimerResponse> getTimer(@PathVariable @Valid UUID id) {
        Optional<Timer> timer = getTimerUseCase.getTimerById(id);
        return timer
                .map(t -> ResponseEntity.ok(TimerResponse.fromDomain(t)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TimerResponse>> getAllTimers(@RequestParam(required = false) @Valid TimerStatus status){
        List<Timer> timers;
        if(status == null){
            timers = getTimerUseCase.getAllTimers();
        } else {
            timers = getTimerUseCase.getTimersByStatus(status);
        }
        List<TimerResponse> response = timers.stream()
                .map(TimerResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TimerResponse> updateTimer(@PathVariable @Valid UUID id, @RequestBody @Valid UpdateTimerRequest req) {
        UpdateTimerCommand updateCommand = new UpdateTimerCommand(id, req.action());
        Timer updatedTimer = updateTimerUseCase.updateTimer(updateCommand);
        return ResponseEntity.ok(TimerResponse.fromDomain(updatedTimer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimer(@PathVariable @Valid UUID id) {
        deleteTimerUseCase.deleteTimerById(id);
        return ResponseEntity.noContent().build();
    }
}
