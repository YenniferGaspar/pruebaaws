package pe.edu.vallegrande.vgmsacademicmanagement.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class Achievement {
    @Id
    private UUID id;
    private UUID capacity_id;
    private UUID institution_id;
    private String code;
    private String description;
    private String age_level;
    private Integer order_index;
    private Boolean is_active;
}
