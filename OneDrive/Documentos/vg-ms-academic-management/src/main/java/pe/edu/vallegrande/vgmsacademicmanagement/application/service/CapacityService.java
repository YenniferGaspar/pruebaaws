package pe.edu.vallegrande.vgmsacademicmanagement.application.service;

import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Capacity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface CapacityService {

    Flux<Capacity> getAll();

    Mono<Capacity> getById(UUID id);

    Mono<Capacity> create(Capacity capacity);

    Mono<Capacity> update(UUID id, Capacity capacity);

    Mono<Void> delete(UUID id);

    Mono<Capacity> restore(UUID id);
}
