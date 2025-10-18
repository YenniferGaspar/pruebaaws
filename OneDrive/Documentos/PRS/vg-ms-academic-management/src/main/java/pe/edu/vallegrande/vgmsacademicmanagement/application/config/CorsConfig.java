package pe.edu.vallegrande.vgmsacademicmanagement.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // ✅ Permite peticiones desde tu frontend React
        config.addAllowedOrigin("http://localhost:5173");
        
        // ✅ Permite todos los métodos HTTP
        config.addAllowedMethod("*");
        
        // ✅ Permite todos los encabezados
        config.addAllowedHeader("*");
        
        // ✅ Permite envío de cookies si fuera necesario
        config.setAllowCredentials(true);

        // Aplica la configuración a todas las rutas del backend
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsWebFilter(source);
    }
}
