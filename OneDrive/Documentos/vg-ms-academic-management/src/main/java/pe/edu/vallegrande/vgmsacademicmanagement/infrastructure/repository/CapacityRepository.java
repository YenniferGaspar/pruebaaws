package pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Capacity;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface CapacityRepository extends ReactiveCrudRepository<Capacity, UUID> {
    Flux<Capacity> findByCompetencyId(UUID competencyId);
}
