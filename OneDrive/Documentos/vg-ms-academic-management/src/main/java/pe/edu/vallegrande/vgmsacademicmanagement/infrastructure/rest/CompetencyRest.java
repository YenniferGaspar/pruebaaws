package pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Competency;
import pe.edu.vallegrande.vgmsacademicmanagement.application.service.CompetencyService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/competencies")
@RequiredArgsConstructor
public class CompetencyRest {

    private final CompetencyService service;

    @Operation(summary = "Listar todas las competencias", description = "Devuelve una lista de todas las competencias registradas en la institución.")
    @GetMapping
    public Flux<Competency> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Buscar competencia por ID", description = "Obtiene una competencia específica mediante su identificador único (UUID).")
    @GetMapping("/{id}")
    public Mono<Competency> getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @Operation(summary = "Registrar una nueva competencia", description = "Crea una nueva competencia y la guarda en la base de datos.")
    @PostMapping
    public Mono<Competency> create(@RequestBody Competency competency) {
        return service.create(competency);
    }

    @Operation(summary = "Actualizar una competencia existente", description = "Permite modificar los datos de una competencia usando su ID.")
    @PutMapping("/{id}")
    public Mono<Competency> update(@PathVariable UUID id, @RequestBody Competency competency) {
        return service.update(id, competency);
    }

    @Operation(summary = "Eliminar (inactivar) una competencia", description = "Realiza una eliminación lógica, marcando la competencia como inactiva.")
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable UUID id) {
        return service.delete(id);
    }

    @Operation(summary = "Restaurar una competencia inactiva", description = "Cambia el estado de la competencia a activa nuevamente.")
    @PutMapping("/restore/{id}")
    public Mono<Competency> restore(@PathVariable UUID id) {
        return service.restore(id);
    }
}
