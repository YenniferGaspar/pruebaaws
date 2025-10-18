package pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vgmsacademicmanagement.application.service.PerformanceService;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Performance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/performances")
@RequiredArgsConstructor
public class PerformanceRest {

    private final PerformanceService performanceService;

    @PostMapping
    public Mono<ResponseEntity<Performance>> create(@RequestBody Mono<Performance> performanceMono) {
        return performanceService.create(performanceMono)
                .map(saved -> ResponseEntity.created(URI.create("/api/performances/" + saved.getId())).body(saved));
    }

    @GetMapping
    public Flux<Performance> getAll() {
        return performanceService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Performance>> getById(@PathVariable UUID id) {
        return performanceService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Performance>> update(@PathVariable UUID id, @RequestBody Mono<Performance> performanceMono) {
        return performanceService.update(id, performanceMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return performanceService.delete(id)
                .thenReturn(ResponseEntity.noContent().<Void>build());
    }
}
