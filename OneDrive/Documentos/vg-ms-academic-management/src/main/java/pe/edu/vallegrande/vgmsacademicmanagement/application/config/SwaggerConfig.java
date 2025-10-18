package pe.edu.vallegrande.vgmsacademicmanagement.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("VG Academic Management API")
                        .version("1.0.0")
                        .description("Microservicio para la gestión académica: Capacities, Competencies, Courses, y más.")
                        .contact(new Contact()
                                .name("Equipo Valle Grande")
                                .email("soporte@vallegrande.edu.pe")
                                .url("https://www.vallegrande.edu.pe"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
