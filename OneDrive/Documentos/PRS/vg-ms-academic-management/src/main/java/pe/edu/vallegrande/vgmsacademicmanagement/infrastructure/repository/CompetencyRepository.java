package pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Competency;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface CompetencyRepository extends ReactiveCrudRepository<Competency, UUID> {

    // 🔹 Buscar todas las competencias por curso
    Flux<Competency> findByCourseId(UUID courseId);
}
