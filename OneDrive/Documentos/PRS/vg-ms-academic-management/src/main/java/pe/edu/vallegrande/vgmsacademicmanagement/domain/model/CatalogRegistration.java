package pe.edu.vallegrande.vgmsacademicmanagement.domain.model;

import lombok.Data;

@Data
public class CatalogRegistration {
    private String institutionId;
    private Course course;
    private Competency competency;
    private Capacity capacity;
    private Performance performance;
}
