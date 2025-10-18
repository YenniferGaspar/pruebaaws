package pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Course;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface CourseRepository extends ReactiveCrudRepository<Course, UUID> {

    Flux<Course> findByInstitutionIdAndIsActive(UUID institutionId, Boolean isActive);
}
