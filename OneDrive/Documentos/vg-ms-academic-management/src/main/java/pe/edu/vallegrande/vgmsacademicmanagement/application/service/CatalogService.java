package pe.edu.vallegrande.vgmsacademicmanagement.application.service;

import pe.edu.vallegrande.vgmsacademicmanagement.domain.model.CatalogRegistration;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface CatalogService {

    Mono<CatalogRegistration> registerAll(CatalogRegistration request);
    Mono<CatalogRegistration> updateAll(CatalogRegistration request);
    Flux<CatalogRegistration> listAll();

    // 🔹 Nuevo: desactivar (cambiar estado a inactivo)
    Mono<Void> deactivate(UUID courseId);

    // 🔹 Nuevo: activar (cambiar estado a activo)
    Mono<Void> activate(UUID courseId);
}
