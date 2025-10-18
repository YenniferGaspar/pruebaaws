package pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.Course;
import pe.edu.vallegrande.vgmsacademicmanagement.application.service.CourseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseRest {

    private final CourseService service;

    @Operation(summary = "Listar todos los cursos", description = "Devuelve una lista de todos los cursos registrados en la institución.")
    @GetMapping
    public Flux<Course> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Buscar curso por ID", description = "Obtiene un curso específico mediante su identificador único (UUID).")
    @GetMapping("/{id}")
    public Mono<Course> getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @Operation(summary = "Registrar un nuevo curso", description = "Crea un nuevo curso y lo guarda en la base de datos.")
    @PostMapping
    public Mono<Course> create(@RequestBody Course course) {
        return service.create(course);
    }

    @Operation(summary = "Actualizar un curso existente", description = "Permite modificar los datos de un curso usando su ID.")
    @PutMapping("/{id}")
    public Mono<Course> update(@PathVariable UUID id, @RequestBody Course course) {
        return service.update(id, course);
    }

    @Operation(summary = "Eliminar (inactivar) un curso", description = "Realiza una eliminación lógica, marcando el curso como inactivo.")
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable UUID id) {
        return service.delete(id);
    }

    @Operation(summary = "Restaurar un curso inactivo", description = "Cambia el estado del curso a activo nuevamente.")
    @PutMapping("/restore/{id}")
    public Mono<Course> restore(@PathVariable UUID id) {
        return service.restore(id);
    }
}

