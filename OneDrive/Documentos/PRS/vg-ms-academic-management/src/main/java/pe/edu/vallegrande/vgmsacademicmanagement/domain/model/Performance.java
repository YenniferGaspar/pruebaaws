package pe.edu.vallegrande.vgmsacademicmanagement.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("performance") // ✅ Indica a qué tabla se mapea
public class Performance {

    @Id // ✅ Indica la clave primaria
    private UUID id;

    private UUID capacityId;
    private UUID institutionId;
    private String code;
    private String description;
    private String ageLevel;
    private Integer orderIndex;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
