package pe.edu.vallegrande.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {
    private int id;
    private String name;
    private String description;
    private String category;
    private String tradeMark;
    private int stock;
    private BigDecimal price;
    private Date expirationDate;
    private char status; // 'A' para activo, 'I' para inactivo
    private String codeProduct; // Código de 4 caracteres
}
