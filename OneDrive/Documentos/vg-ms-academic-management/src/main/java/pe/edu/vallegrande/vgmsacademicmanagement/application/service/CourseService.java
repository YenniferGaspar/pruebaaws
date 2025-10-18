package pe.edu.vallegrande.vgmsacademicmanagement.application.service;

import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Course;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CourseService {

    Flux<Course> getAll();
    Mono<Course> getById(UUID id);
    Mono<Course> create(Course course);
    Mono<Course> update(UUID id, Course course);
    Mono<Void> delete(UUID id);
    Mono<Course> restore(UUID id);
}

