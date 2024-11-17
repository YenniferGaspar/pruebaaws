package pe.edu.vallegrande.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pe.edu.vallegrande.db.ConexionDB;
import pe.edu.vallegrande.model.Customer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

public class CustomerService {

    // Método para listar clientes
    public List<Customer> listarActivos() {
        List<Customer> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes.customer WHERE status = 'A'"; // Solo clientes activos
        try (Connection connection = ConexionDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Customer cliente = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("document_type"),
                        resultSet.getString("number_document"),
                        resultSet.getDate("birthdate"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("address"),
                        resultSet.getString("status").charAt(0)
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }


    public List<Customer> listarInactivos() {
        List<Customer> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes.customer WHERE status = 'I'"; // Solo clientes inactivos
        try (Connection connection = ConexionDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Customer cliente = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("document_type"),
                        resultSet.getString("number_document"),
                        resultSet.getDate("birthdate"), // Fecha de nacimiento
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("address"),
                        resultSet.getString("status").charAt(0) // Convertir estado a char (I para inactivo)
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    // Método para agregar un nuevo cliente
    public int agregar(Customer cliente) {
        String sql = "INSERT INTO Clientes.customer (name, last_name, document_type, number_document, birthdate, phone, email, address, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int filasAfectadas = 0;
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cliente.getName()); // 1. name
            preparedStatement.setString(2, cliente.getLastName()); // 2. last_name
            preparedStatement.setString(3, cliente.getDocumentType()); // 3. document_type
            preparedStatement.setString(4, cliente.getNumberDocument()); // 4. number_document

            // Conversión de java.util.Date a java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(cliente.getBirthdate().getTime());
            preparedStatement.setDate(5, sqlDate); // 5. birthdate

            preparedStatement.setString(6, cliente.getPhone()); // 6. phone
            preparedStatement.setString(7, cliente.getEmail()); // 7. email
            preparedStatement.setString(8, cliente.getAddress()); // 8. address
            preparedStatement.setString(9, String.valueOf(cliente.getStatus())); // 9. status

            filasAfectadas = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Verificar si la excepción es por violación de clave única (si aplica)
            if (e.getMessage().contains("Violation of UNIQUE KEY constraint")) {
                throw new RuntimeException("El número de documento ya está en uso. Por favor, ingrese uno diferente.");
            } else {
                System.err.println("Error inesperado en la base de datos: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Error inesperado en la base de datos.");
            }
        }
        return filasAfectadas;
    }

    // Método para editar un cliente
    public int editar(Customer cliente) {
        String sql = "UPDATE Clientes.customer SET name = ?, last_name = ?, document_type = ?, number_document = ?, phone = ?, email = ?, birthdate = ?, address = ?, status = ? WHERE id = ?";
        int filasAfectadas = 0;
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cliente.getName()); // 1. name
            preparedStatement.setString(2, cliente.getLastName()); // 2. last_name
            preparedStatement.setString(3, cliente.getDocumentType()); // 3. document_type
            preparedStatement.setString(4, cliente.getNumberDocument()); // 4. number_document
            preparedStatement.setString(5, cliente.getPhone()); // 5. phone
            preparedStatement.setString(6, cliente.getEmail()); // 6. email

            // Conversión de java.util.Date a java.sql.Date para birthdate
            java.sql.Date sqlDate = new java.sql.Date(cliente.getBirthdate().getTime());
            preparedStatement.setDate(7, sqlDate); // 7. birthdate

            preparedStatement.setString(8, cliente.getAddress()); // 8. address
            preparedStatement.setString(9, String.valueOf(cliente.getStatus())); // 9. status
            preparedStatement.setInt(10, cliente.getId()); // 10. id

            filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Cliente actualizado exitosamente.");
            } else {
                System.out.println("No se encontró ningún cliente con el ID: " + cliente.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }


    // Método para eliminar un cliente por su ID (cambiar estado a inactivo)
    public void eliminar(int id) {
        String sql = "UPDATE Clientes.customer SET status = 'I' WHERE id = ?"; // Cambiar el estado a inactivo
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate(); // Ejecutar la actualización
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
    }

    // Método para eliminar un cliente de manera definitiva por su ID
    public void eliminarDefinitivoPorId(int id) {
        String sql = "DELETE FROM Clientes.customer WHERE id = ? AND status = 'I'"; // Eliminar solo el cliente inactivo con el ID específico
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int filasEliminadas = preparedStatement.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Cliente inactivo con ID " + id + " eliminado permanentemente.");
            } else {
                System.out.println("No se encontró un cliente inactivo con el ID " + id + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
    }

    // Método para restaurar un cliente
    public boolean restaurarCustomer(int id) {
        String sql = "UPDATE Clientes.customer SET status = 'A' WHERE id = ?"; // Cambiar estado a activo
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Devuelve true si se actualizó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
        return false; // Devuelve false si hubo un error
    }


    // Método para buscar un cliente por su ID
    public Customer buscarPorId(int id) {
        Customer cliente = null;
        String sql = "SELECT * FROM Clientes.customer WHERE id = ?";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cliente = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("document_type"),
                        resultSet.getString("number_document"),
                        resultSet.getDate("birthdate"), // Obtener birthdate
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("address"), // Obtener address
                        resultSet.getString("status").charAt(0) // Convierte a char
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    public void exportarA_pdf(List<Customer> clientes, String rutaArchivo) {
        try {
            PdfWriter writer = new PdfWriter(rutaArchivo);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Agregar título
            document.add(new Paragraph("Lista de Clientes"));

            // Crear tabla
            Table table = new Table(5); // 5 columnas
            table.addHeaderCell("ID");
            table.addHeaderCell("Nombre");
            table.addHeaderCell("Apellido");
            table.addHeaderCell("Email");
            table.addHeaderCell("Teléfono");
            table.addHeaderCell("Dirección");

            for (Customer cliente : clientes) {
                table.addCell(String.valueOf(cliente.getId()));
                table.addCell(cliente.getName());
                table.addCell(cliente.getLastName());
                table.addCell(cliente.getEmail());
                table.addCell(cliente.getPhone());
                table.addCell(cliente.getAddress());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportarA_excel(List<Customer> clientes, String rutaArchivo) {
        Workbook workbook = new XSSFWorkbook(); // Crear un nuevo libro de trabajo
        Sheet sheet = workbook.createSheet("Clientes"); // Crear una nueva hoja

        // Crear la fila del encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nombre");
        headerRow.createCell(2).setCellValue("Apellido");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Teléfono");

        // Agregar los datos de los clientes
        int rowNum = 1;
        for (Customer cliente : clientes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(cliente.getId());
            row.createCell(1).setCellValue(cliente.getName());
            row.createCell(2).setCellValue(cliente.getLastName());
            row.createCell(3).setCellValue(cliente.getEmail());
            row.createCell(4).setCellValue(cliente.getPhone());
        }

        // Ajustar el tamaño de las columnas
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        // Guardar el archivo
        try (FileOutputStream fileOut = new FileOutputStream(rutaArchivo)) {
            workbook.write(fileOut); // Escribir el libro de trabajo en el archivo
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close(); // Cerrar el libro de trabajo
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Customer> buscarClientesActivos(String query) {
        List<Customer> results = new ArrayList<>();
        String sql = "SELECT * FROM Clientes.customer WHERE (last_name LIKE ? OR number_document LIKE ?) AND status = 'A'";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchPattern = "%" + query + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setDocumentType(resultSet.getString("document_type"));
                customer.setNumberDocument(resultSet.getString("number_document"));
                customer.setBirthdate(resultSet.getDate("birthdate"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setEmail(resultSet.getString("email"));
                customer.setAddress(resultSet.getString("address"));
                customer.setStatus(resultSet.getString("status").charAt(0));
                results.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Para fines de depuración
            // Aquí podrías lanzar una excepción personalizada si lo deseas
        }

        return results;

    }


}
