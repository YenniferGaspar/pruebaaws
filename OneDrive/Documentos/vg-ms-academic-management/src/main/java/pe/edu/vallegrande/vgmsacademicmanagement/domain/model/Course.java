package pe.edu.vallegrande.vgmsacademicmanagement.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("courses")
public class Course {

    @Id
    private UUID id;

    @Column("institution_id")
    private UUID institutionId;

    private String code;
    private String name;

    @Column("area_curricular")
    private String areaCurricular;

    @Column("age_level")
    private String ageLevel;

    private String description;

    @Column("is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
