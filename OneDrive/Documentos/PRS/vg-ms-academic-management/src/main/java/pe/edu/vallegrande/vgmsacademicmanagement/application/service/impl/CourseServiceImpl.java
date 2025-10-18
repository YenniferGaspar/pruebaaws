package pe.edu.vallegrande.vgmsacademicmanagement.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsacademicmanagement.application.service.CourseService;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Course;
import pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.repository.CourseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    @Override
    public Flux<Course> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Course> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Course> create(Course course) {
        // ⚙️ Asignar institution_id temporal
        course.setInstitutionId(UUID.fromString("11111111-1111-1111-1111-111111111111"));

        LocalDateTime now = LocalDateTime.now();
        course.setCreatedAt(now);
        course.setUpdatedAt(now);

        return repository.save(course);
    }

    @Override
    public Mono<Course> update(UUID id, Course course) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setCode(course.getCode());
                    existing.setName(course.getName());
                    existing.setAreaCurricular(course.getAreaCurricular());
                    existing.setAgeLevel(course.getAgeLevel());
                    existing.setDescription(course.getDescription());
                    existing.setUpdatedAt(LocalDateTime.now());

                    if (course.getIsActive() != null) {
                        existing.setIsActive(course.getIsActive());
                    }

                    return repository.save(existing);
                });
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setIsActive(false);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existing);
                })
                .then();
    }

    @Override
    public Mono<Course> restore(UUID id) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setIsActive(true);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existing);
                });
    }
}
