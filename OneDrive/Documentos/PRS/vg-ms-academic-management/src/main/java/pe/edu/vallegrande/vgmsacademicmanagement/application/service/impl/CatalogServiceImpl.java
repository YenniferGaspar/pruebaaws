package pe.edu.vallegrande.vgmsacademicmanagement.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsacademicmanagement.application.service.CatalogService;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.*;
import pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.repository.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CourseRepository courseRepository;
    private final CompetencyRepository competencyRepository;
    private final CapacityRepository capacityRepository;
    private final PerformanceRepository performanceRepository;

    @Override
    public Flux<CatalogRegistration> listAll() {
        return courseRepository.findAll()
                .flatMap(course ->
                        competencyRepository.findByCourseId(course.getId())
                                .flatMap(competency ->
                                        capacityRepository.findByCompetencyId(competency.getId())
                                                .flatMap(capacity ->
                                                        performanceRepository.findByCapacityId(capacity.getId())
                                                                .map(performance -> {
                                                                    CatalogRegistration registration = new CatalogRegistration();
                                                                    registration.setInstitutionId(course.getInstitutionId().toString());
                                                                    registration.setCourse(course);
                                                                    registration.setCompetency(competency);
                                                                    registration.setCapacity(capacity);
                                                                    registration.setPerformance(performance);
                                                                    return registration;
                                                                })
                                                )
                                )
                );
    }

    @Override
    public Mono<CatalogRegistration> registerAll(CatalogRegistration request) {
        UUID institutionId = UUID.fromString(request.getInstitutionId());

        Course course = request.getCourse();
        Competency competency = request.getCompetency();
        Capacity capacity = request.getCapacity();
        Performance performance = request.getPerformance();

        course.setInstitutionId(institutionId);
        competency.setInstitutionId(institutionId);
        capacity.setInstitutionId(institutionId);
        performance.setInstitutionId(institutionId);

        return courseRepository.save(course)
                .flatMap(savedCourse -> {
                    competency.setCourseId(savedCourse.getId());
                    return competencyRepository.save(competency);
                })
                .flatMap(savedCompetency -> {
                    capacity.setCompetencyId(savedCompetency.getId());
                    return capacityRepository.save(capacity);
                })
                .flatMap(savedCapacity -> {
                    performance.setCapacityId(savedCapacity.getId());
                    return performanceRepository.save(performance);
                })
                .thenReturn(request);
    }

    @Override
    public Mono<CatalogRegistration> updateAll(CatalogRegistration request) {
        UUID institutionId = UUID.fromString(request.getInstitutionId());

        Course course = request.getCourse();
        Competency competency = request.getCompetency();
        Capacity capacity = request.getCapacity();
        Performance performance = request.getPerformance();

        course.setInstitutionId(institutionId);
        competency.setInstitutionId(institutionId);
        capacity.setInstitutionId(institutionId);
        performance.setInstitutionId(institutionId);

        return courseRepository.findById(course.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("Course not found with ID: " + course.getId())))
                .flatMap(existingCourse -> {
                    existingCourse.setCode(course.getCode());
                    existingCourse.setName(course.getName());
                    existingCourse.setAreaCurricular(course.getAreaCurricular());
                    existingCourse.setAgeLevel(course.getAgeLevel());
                    existingCourse.setDescription(course.getDescription());
                    existingCourse.setIsActive(course.getIsActive());
                    return courseRepository.save(existingCourse);
                })
                .flatMap(updatedCourse -> competencyRepository.findById(competency.getId())
                        .switchIfEmpty(Mono.error(new RuntimeException("Competency not found with ID: " + competency.getId())))
                        .flatMap(existingCompetency -> {
                            existingCompetency.setCode(competency.getCode());
                            existingCompetency.setName(competency.getName());
                            existingCompetency.setDescription(competency.getDescription());
                            existingCompetency.setOrderIndex(competency.getOrderIndex());
                            existingCompetency.setIsActive(competency.getIsActive());
                            return competencyRepository.save(existingCompetency);
                        })
                )
                .flatMap(updatedCompetency -> capacityRepository.findById(capacity.getId())
                        .switchIfEmpty(Mono.error(new RuntimeException("Capacity not found with ID: " + capacity.getId())))
                        .flatMap(existingCapacity -> {
                            existingCapacity.setCode(capacity.getCode());
                            existingCapacity.setName(capacity.getName());
                            existingCapacity.setDescription(capacity.getDescription());
                            existingCapacity.setOrderIndex(capacity.getOrderIndex());
                            existingCapacity.setIsActive(capacity.getIsActive());
                            return capacityRepository.save(existingCapacity);
                        })
                )
                .flatMap(updatedCapacity -> performanceRepository.findById(performance.getId())
                        .switchIfEmpty(Mono.error(new RuntimeException("Performance not found with ID: " + performance.getId())))
                        .flatMap(existingPerformance -> {
                            existingPerformance.setCode(performance.getCode());
                            existingPerformance.setDescription(performance.getDescription());
                            existingPerformance.setAgeLevel(performance.getAgeLevel());
                            existingPerformance.setOrderIndex(performance.getOrderIndex());
                            existingPerformance.setIsActive(performance.getIsActive());
                            return performanceRepository.save(existingPerformance);
                        })
                )
                .thenReturn(request);
    }

    // 🟥 Desactivar todo (Course + Competency + Capacity + Performance)
@Override
public Mono<Void> deactivate(UUID courseId) {
    return courseRepository.findById(courseId)
        .flatMap(course -> {
            course.setIsActive(false);
            return courseRepository.save(course);
        })
        .thenMany(competencyRepository.findByCourseId(courseId)
            .flatMap(competency -> {
                competency.setIsActive(false);
                return competencyRepository.save(competency)
                    .thenMany(capacityRepository.findByCompetencyId(competency.getId())
                        .flatMap(capacity -> {
                            capacity.setIsActive(false);
                            return capacityRepository.save(capacity)
                                .thenMany(performanceRepository.findByCapacityId(capacity.getId())
                                    .flatMap(performance -> {
                                        performance.setIsActive(false);
                                        return performanceRepository.save(performance);
                                    })
                                );
                        })
                    );
            })
        )
        .then();
}

// 🟩 Activar todo (Course + Competency + Capacity + Performance)
@Override
public Mono<Void> activate(UUID courseId) {
    return courseRepository.findById(courseId)
        .flatMap(course -> {
            course.setIsActive(true);
            return courseRepository.save(course);
        })
        .thenMany(competencyRepository.findByCourseId(courseId)
            .flatMap(competency -> {
                competency.setIsActive(true);
                return competencyRepository.save(competency)
                    .thenMany(capacityRepository.findByCompetencyId(competency.getId())
                        .flatMap(capacity -> {
                            capacity.setIsActive(true);
                            return capacityRepository.save(capacity)
                                .thenMany(performanceRepository.findByCapacityId(capacity.getId())
                                    .flatMap(performance -> {
                                        performance.setIsActive(true);
                                        return performanceRepository.save(performance);
                                    })
                                );
                        })
                    );
            })
        )
        .then();
}

}
