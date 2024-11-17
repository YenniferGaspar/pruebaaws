package pe.edu.vallegrande.service;


import pe.edu.vallegrande.db.ConexionDB;
import pe.edu.vallegrande.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierService {


    public List<Supplier> listar() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";
        try (Connection connection = ConexionDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Supplier supplier = new Supplier(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("direction"),
                        resultSet.getString("email")
                );
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }


    public int agregar(Supplier supplier) {
        String sql = "INSERT INTO supplier (name, direction, email) VALUES (?, ?, ?)";
        int filasAfectadas = 0;
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getDirection());
            preparedStatement.setString(3, supplier.getEmail());

            filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Proveedor agregado exitosamente.");
            } else {
                System.out.println("No se pudo agregar el proveedor.");
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar proveedor: " + e.getMessage());
            e.printStackTrace();
        }
        return filasAfectadas;
    }


    public int editar(Supplier supplier) {
        String sql = "UPDATE supplier SET name = ?, direction = ?, email = ? WHERE id = ?";
        int filasAfectadas = 0;
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getDirection());
            preparedStatement.setString(3, supplier.getEmail());
            preparedStatement.setInt(4, supplier.getId());

            filasAfectadas = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }


    public int eliminar(int id) {
        String sql = "DELETE FROM supplier WHERE id = ?";
        int filasAfectadas = 0;
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            filasAfectadas = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }


    public Supplier buscarPorId(int id) {
        Supplier supplier = null;
        String sql = "SELECT * FROM supplier WHERE id = ?";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                supplier = new Supplier(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("direction"),
                        resultSet.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }


}
