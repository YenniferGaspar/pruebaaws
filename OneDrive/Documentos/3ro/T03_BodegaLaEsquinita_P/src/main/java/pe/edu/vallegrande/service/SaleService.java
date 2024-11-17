package pe.edu.vallegrande.service;

import pe.edu.vallegrande.model.Sale;
import java.sql.SQLException;
import pe.edu.vallegrande.db.ConexionDB;
import pe.edu.vallegrande.model.Sale;
import pe.edu.vallegrande.model.Product;
import java.sql.*;

public class SaleService {
    public void realizarVenta(Sale venta, Product producto) {
        String sqlInsertVenta = "INSERT INTO sale (sale_date, total_amount, customer_id) VALUES (?, ?, ?)";
        String sqlUpdateStock = "UPDATE Productos.product SET stock = ? WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatementInsertVenta = null;
        PreparedStatement preparedStatementUpdateStock = null;

        try {
            // Establecemos la conexión y desactivamos el auto-commit para iniciar la transacción
            connection = ConexionDB.getConnection();
            connection.setAutoCommit(false);  // Comenzamos la transacción

            // Primero, registrar la venta
            preparedStatementInsertVenta = connection.prepareStatement(sqlInsertVenta, Statement.RETURN_GENERATED_KEYS);
            preparedStatementInsertVenta.setDate(1, venta.getDateSale());  // Aquí se usa getDateSale()
            preparedStatementInsertVenta.setBigDecimal(2, venta.getTotalSale());  // Aquí se usa getTotalSale()
            preparedStatementInsertVenta.setInt(3, venta.getCustomerId());

            // Ejecutar la inserción de la venta
            preparedStatementInsertVenta.executeUpdate();

            // Obtener el ID de la venta generada automáticamente (si es necesario para otras operaciones)
            ResultSet generatedKeys = preparedStatementInsertVenta.getGeneratedKeys();
            if (generatedKeys.next()) {
                int saleId = generatedKeys.getInt(1);
                System.out.println("Venta registrada con éxito, ID de venta: " + saleId);

                // Luego, actualizar el stock del producto vendido
                preparedStatementUpdateStock = connection.prepareStatement(sqlUpdateStock);
                int nuevoStock = producto.getStock() - venta.getQuantity();  // Actualiza el stock según la cantidad vendida
                preparedStatementUpdateStock.setInt(1, nuevoStock);
                preparedStatementUpdateStock.setInt(2, producto.getId());

                // Ejecutar la actualización de stock
                preparedStatementUpdateStock.executeUpdate();

                // Si todo salió bien, confirmamos la transacción
                connection.commit();
                System.out.println("Venta completada con éxito y stock actualizado.");
            }

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    // Si algo falla, revertimos la transacción
                    connection.rollback();
                    System.out.println("Transacción fallida, cambios revertidos.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                // Asegurarnos de restaurar el auto-commit y cerrar los recursos
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
                if (preparedStatementInsertVenta != null) {
                    preparedStatementInsertVenta.close();
                }
                if (preparedStatementUpdateStock != null) {
                    preparedStatementUpdateStock.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void agregarVenta(Sale venta) throws SQLException {
        String sqlInsert = "INSERT INTO sales (customer_id, product_id, quantity, total_sale, sale_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlInsert)) {

            // Asignar los parámetros a la consulta
            ps.setInt(1, venta.getCustomerId());
            ps.setInt(2, venta.getProductId());  // Suponiendo que Sale tiene un campo productId
            ps.setInt(3, venta.getQuantity());
            ps.setBigDecimal(4, venta.getTotalSale());
            ps.setDate(5, venta.getDateSale());

            // Ejecutar la inserción
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al agregar la venta");
        }
    }

    public void actualizarStock(Product producto, int cantidadVendida) throws SQLException {
        String sqlUpdate = "UPDATE Productos.product SET stock = stock - ? WHERE id = ?";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {

            // Asignar los parámetros a la consulta
            ps.setInt(1, cantidadVendida);
            ps.setInt(2, producto.getId());

            // Ejecutar la actualización del stock
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al actualizar el stock");
        }
    }
}
