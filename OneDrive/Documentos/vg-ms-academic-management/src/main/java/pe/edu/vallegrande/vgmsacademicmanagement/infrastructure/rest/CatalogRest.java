package pe.edu.vallegrande.vgmsacademicmanagement.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vgmsacademicmanagement.application.service.CatalogService;
import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.CatalogRegistration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID; // ✅ IMPORT NECESARIO

@RestController
@RequestMapping("/api/catalogs")
@RequiredArgsConstructor
public class CatalogRest {

    private final CatalogService service;

    // 🔹 Registrar todo (course, competency, capacity, performance)
    @PostMapping("/register-all")
    public Mono<CatalogRegistration> registerAll(@RequestBody CatalogRegistration request) {
        return service.registerAll(request);
    }

    // 🔹 Actualizar todo (usando los IDs existentes)
    @PutMapping("/update-all")
    public Mono<CatalogRegistration> updateAll(@RequestBody CatalogRegistration request) {
        return service.updateAll(request);
    }

    // 🔹 Listar todo
    @GetMapping("/list-all")
    public Flux<CatalogRegistration> listAll() {
        return service.listAll();
    }

    // 🔹 Eliminar (cambia a inactivo)
    @PutMapping("/deactivate/{courseId}")
    public Mono<Void> deactivate(@PathVariable UUID courseId) {
        return service.deactivate(courseId);
    }

    // 🔹 Restaurar (cambia a activo)
    @PutMapping("/activate/{courseId}")
    public Mono<Void> activate(@PathVariable UUID courseId) {
        return service.activate(courseId);
    }
}
