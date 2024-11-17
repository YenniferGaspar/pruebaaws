package pe.edu.vallegrande.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    private int id;
    private int customerId;
    private int sellerId;
    private Date dateSale;  // Método generado: getDateSale()
    private String paymentMethod;
    private int productId;
    private int quantity;
    private char status;
    private BigDecimal totalSale;  // Método generado: getTotalSale()
}
