package pe.edu.vallegrande.vgmsacademicmanagement.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsacademicmanagement.application.service.PerformanceService;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Performance;
import pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.repository.PerformanceRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;

    @Override
    public Mono<Performance> create(Mono<Performance> performanceMono) {
        return performanceMono.flatMap(performance -> {
            performance.setId(UUID.randomUUID());
            performance.setIsActive(true);
            performance.setCreatedAt(LocalDateTime.now());
            performance.setUpdatedAt(LocalDateTime.now());
            return performanceRepository.save(performance);
        });
    }

    @Override
    public Mono<Performance> getById(UUID id) {
        return performanceRepository.findById(id);
    }

    @Override
    public Flux<Performance> getAll() {
        return performanceRepository.findAll();
    }

    @Override
    public Mono<Performance> update(UUID id, Mono<Performance> performanceMono) {
        return performanceRepository.findById(id)
                .flatMap(existing -> performanceMono.flatMap(p -> {
                    existing.setCapacityId(p.getCapacityId());
                    existing.setInstitutionId(p.getInstitutionId());
                    existing.setCode(p.getCode());
                    existing.setDescription(p.getDescription());
                    existing.setAgeLevel(p.getAgeLevel());
                    existing.setOrderIndex(p.getOrderIndex());
                    existing.setIsActive(p.getIsActive());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return performanceRepository.save(existing);
                }));
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return performanceRepository.deleteById(id);
    }
}
