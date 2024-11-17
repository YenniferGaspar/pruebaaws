package pe.edu.vallegrande.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private int id;
    private String name;
    private String lastName;
    private String documentType;
    private String numberDocument;
    private Date birthdate; // Agregar el campo birthdate
    private String phone;
    private String email;
    private String address;
    private char status;
}
