package com.br.app.clock.timer.adapter.out.persistence;

import com.br.app.clock.timer.domain.model.TimerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SpringDataTimerRepository extends JpaRepository<TimerJpaEntity, UUID> {

    List<TimerJpaEntity> findByStatus(TimerStatus status);

}
