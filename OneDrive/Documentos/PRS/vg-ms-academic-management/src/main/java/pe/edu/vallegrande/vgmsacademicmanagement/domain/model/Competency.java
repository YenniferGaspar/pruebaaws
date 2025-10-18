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
@Table("competencies")
public class Competency {

    @Id
    private UUID id;

    @Column("course_id")
    private UUID courseId;

    @Column("institution_id")
    private UUID institutionId;

    private String code;
    private String name;
    private String description;

    @Column("order_index")
    private Integer orderIndex;

    @Column("is_active")
    @Builder.Default
    private Boolean isActive = true;


    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
