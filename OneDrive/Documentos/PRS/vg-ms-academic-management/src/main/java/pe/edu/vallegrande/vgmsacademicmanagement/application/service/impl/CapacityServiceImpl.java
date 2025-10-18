package pe.edu.vallegrande.vgmsacademicmanagement.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsacademicmanagement.application.service.CapacityService;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Capacity;
import pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.repository.CapacityRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CapacityServiceImpl implements CapacityService {

    private final CapacityRepository repository;

    @Override
    public Flux<Capacity> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Capacity> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Capacity> create(Capacity capacity) {
        // ⚙️ Asignar institution_id temporal
        capacity.setInstitutionId(UUID.fromString("11111111-1111-1111-1111-111111111111"));

        LocalDateTime now = LocalDateTime.now();
        capacity.setCreatedAt(now);
        capacity.setUpdatedAt(now);

        // Valor por defecto activo
        capacity.setIsActive(true);

        return repository.save(capacity);
    }

    @Override
    public Mono<Capacity> update(UUID id, Capacity capacity) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setName(capacity.getName());
                    existing.setDescription(capacity.getDescription());
                    existing.setCode(capacity.getCode());
                    existing.setCompetencyId(capacity.getCompetencyId());
                    existing.setUpdatedAt(LocalDateTime.now());

                    // ✅ Actualizar estado si viene del frontend
                    if (capacity.getIsActive() != null) {
                        existing.setIsActive(capacity.getIsActive());
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
    public Mono<Capacity> restore(UUID id) {
        // ♻️ Restaurar capacidad inactiva
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setIsActive(true); // activa
                    existing.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existing);
                });
    }
}
