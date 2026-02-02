package com.br.app.clock.timer.adapter.in.web;

import com.br.app.clock.timer.application.port.in.*;
import com.br.app.clock.timer.domain.model.Timer;
import com.br.app.clock.timer.domain.model.TimerStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/timers")
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
    public ResponseEntity<Timer> createTimer(@RequestBody CreateTimerCommand command) {
        Timer timer = createTimerUseCase.createTimer(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(timer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Timer> getTimer(@PathVariable UUID id) {
        Optional<Timer> timer = getTimerUseCase.getTimerById(id);
        return timer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<Timer>> getAllTimers(@RequestParam(required = false)TimerStatus status){
        List<Timer> timers;
        if(status == null){
            timers = getTimerUseCase.getAllTimers();
        } else {
            timers = getTimerUseCase.getTimersByStatus(status);
        }
        return ResponseEntity.ok(timers);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Timer> updateTimer(@PathVariable UUID id, @RequestBody UpdateTimerCommand command) {
        UpdateTimerCommand updateCommand = new UpdateTimerCommand(id, command.action());
        Timer updatedTimer = updateTimerUseCase.updateTimer(updateCommand);
        return ResponseEntity.ok(updatedTimer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimer(@PathVariable UUID id) {
        deleteTimerUseCase.deleteTimerById(id);
        return ResponseEntity.noContent().build();
    }
}
