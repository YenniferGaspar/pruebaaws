package pe.edu.vallegrande.vgmsacademicmanagement.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsacademicmanagement.application.service.CompetencyService;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Competency;
import pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.repository.CompetencyRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompetencyServiceImpl implements CompetencyService {

    private final CompetencyRepository repository;

    @Override
    public Flux<Competency> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Competency> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Competency> create(Competency competency) {
        // ⚙️ Asignar institution_id temporal
        competency.setInstitutionId(UUID.fromString("11111111-1111-1111-1111-111111111111"));

        // courseId se espera que venga del frontend
        LocalDateTime now = LocalDateTime.now();
        competency.setCreatedAt(now);
        competency.setUpdatedAt(now);

        // isActive ya tiene valor por defecto en el modelo (true = activo)
        return repository.save(competency);
    }

    @Override
    public Mono<Competency> update(UUID id, Competency competency) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setCode(competency.getCode());
                    existing.setName(competency.getName());
                    existing.setDescription(competency.getDescription());
                    existing.setOrderIndex(competency.getOrderIndex());
                    existing.setCourseId(competency.getCourseId());
                    existing.setUpdatedAt(LocalDateTime.now());

                    // ✅ Opcional: actualizar el estado si viene del frontend como A/I
                    if (competency.getIsActive() != null) {
                        existing.setIsActive(competency.getIsActive());
                    }

                    return repository.save(existing);
                });
    }

    @Override
    public Mono<Void> delete(UUID id) {
        // 🔥 Eliminación lógica (soft delete)
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setIsActive(false); // inactiva
                    existing.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existing);
                })
                .then();
    }

    @Override
    public Mono<Competency> restore(UUID id) {
        // ♻️ Restaurar competencia inactiva
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setIsActive(true); // activa
                    existing.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existing);
                });
    }
}
