package pe.edu.vallegrande.vgmsacademicmanagement.application.service;

import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Competency;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CompetencyService {

    Flux<Competency> getAll();
    Mono<Competency> getById(UUID id);
    Mono<Competency> create(Competency competency);
    Mono<Competency> update(UUID id, Competency competency);
    Mono<Void> delete(UUID id);
    Mono<Competency> restore(UUID id);
}
