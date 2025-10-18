package pe.edu.vallegrande.vgmsacademicmanagement.application.service;

import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Performance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PerformanceService {

    Mono<Performance> create(Mono<Performance> performanceMono);

    Mono<Performance> getById(UUID id);

    Flux<Performance> getAll();

    Mono<Performance> update(UUID id, Mono<Performance> performanceMono);

    Mono<Void> delete(UUID id);
}
