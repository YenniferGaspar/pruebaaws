package pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Capacity;
import pe.edu.vallegrande.vgmsacademicmanagement.application.service.CapacityService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/capacities")
@RequiredArgsConstructor
@Tag(name = "Capacities", description = "Endpoints para la gestión de Capacidades")
public class CapacityRest {

    private final CapacityService service;

    @GetMapping
    @Operation(summary = "Listar todas las capacidades", description = "Obtiene todas las capacidades registradas en el sistema.")
    public Flux<Capacity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar capacidad por ID", description = "Obtiene una capacidad específica a partir de su identificador único (UUID).")
    public Mono<Capacity> getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva capacidad", description = "Registra una nueva capacidad en la base de datos con institution_id temporal.")
    public Mono<Capacity> create(@RequestBody Capacity capacity) {
        return service.create(capacity);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una capacidad", description = "Modifica los datos de una capacidad existente según su ID.")
    public Mono<Capacity> update(@PathVariable UUID id, @RequestBody Capacity capacity) {
        return service.update(id, capacity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inactivar una capacidad", description = "Realiza una eliminación lógica (cambia is_active a false).")
    public Mono<Void> delete(@PathVariable UUID id) {
        return service.delete(id);
    }

    @PutMapping("/restore/{id}")
    @Operation(summary = "Restaurar una capacidad", description = "Activa nuevamente una capacidad previamente inactiva.")
    public Mono<Capacity> restore(@PathVariable UUID id) {
        return service.restore(id);
    }
}
