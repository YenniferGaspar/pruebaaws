package pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Performance;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface PerformanceRepository extends ReactiveCrudRepository<Performance, UUID> {
    Flux<Performance> findByCapacityId(UUID capacityId);
}
