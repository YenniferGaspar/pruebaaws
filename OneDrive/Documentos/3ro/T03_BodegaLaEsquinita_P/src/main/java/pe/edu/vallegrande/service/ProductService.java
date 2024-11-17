package pe.edu.vallegrande.service;

import pe.edu.vallegrande.db.ConexionDB;
import pe.edu.vallegrande.model.Product;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ProductService {

    public List<Product> listarActivos() {
        List<Product> productos = new ArrayList<>();
        String sql = "SELECT * FROM Productos.product WHERE status = 'A'"; // Solo productos activos
        try (Connection connection = ConexionDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Product producto = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getString("trade_mark"),
                        resultSet.getInt("stock"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getDate("expiration_date"),
                        resultSet.getString("status").charAt(0), // Agregar estado (A/I)
                        resultSet.getString("code_product") // Agregar código de producto
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public List<Product> listarInactivos() {
        List<Product> productos = new ArrayList<>();
        String sql = "SELECT * FROM Productos.product WHERE status = 'I'"; // Solo productos inactivos
        try (Connection connection = ConexionDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Product producto = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getString("trade_mark"),
                        resultSet.getInt("stock"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getDate("expiration_date"),
                        resultSet.getString("status").charAt(0), // Agregar estado (A/I)
                        resultSet.getString("code_product") // Agregar código de producto
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    // Método para agregar un nuevo producto
    public int agregar(Product producto) {
        String sql = "INSERT INTO Productos.product (name, description, category, trade_mark, stock, price, expiration_date, status, code_product) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int filasAfectadas = 0;

        // Intenta conectar con la base de datos y ejecutar la inserción
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, producto.getName());
            preparedStatement.setString(2, producto.getDescription());
            preparedStatement.setString(3, producto.getCategory());
            preparedStatement.setString(4, producto.getTradeMark());
            preparedStatement.setInt(5, producto.getStock());
            preparedStatement.setBigDecimal(6, producto.getPrice());
            preparedStatement.setDate(7, producto.getExpirationDate());
            preparedStatement.setString(8, String.valueOf(producto.getStatus()));  // Estado 'A' por defecto
            preparedStatement.setString(9, producto.getCodeProduct());  // Código del producto

            // Ejecuta la actualización
            filasAfectadas = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Manejo de excepciones si la clave única ya existe o hay otros problemas
            if (e.getMessage().contains("Violation of UNIQUE KEY constraint")) {
                throw new RuntimeException("El código del producto ya está en uso. Por favor, ingrese uno diferente.");
            } else {
                throw new RuntimeException("Error inesperado en la base de datos. Intente nuevamente.");
            }
        }
        return filasAfectadas;
    }

    // Método para editar un producto
    public int editar(Product producto) {
        String sql = "UPDATE Productos.product SET name = ?, description = ?, category = ?, trade_mark = ?, stock = ?, price = ?, expiration_date = ?, status = ?, code_product = ? WHERE id = ?";
        int filasAfectadas = 0;
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, producto.getName()); // 1. name
            preparedStatement.setString(2, producto.getDescription()); // 2. description
            preparedStatement.setString(3, producto.getCategory()); // 3. category
            preparedStatement.setString(4, producto.getTradeMark()); // 4. trade_mark
            preparedStatement.setInt(5, producto.getStock()); // 5. stock
            preparedStatement.setBigDecimal(6, producto.getPrice()); // 6. price

            // Conversión de java.util.Date a java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(producto.getExpirationDate().getTime());
            preparedStatement.setDate(7, sqlDate); // 7. expiration_date

            preparedStatement.setString(8, String.valueOf(producto.getStatus())); // 8. status
            preparedStatement.setString(9, producto.getCodeProduct()); // 9. code_product
            preparedStatement.setInt(10, producto.getId()); // 10. id

            filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto actualizado exitosamente.");
            } else {
                System.out.println("No se encontró ningún producto con el ID: " + producto.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    // Método para buscar un producto por su ID
    public Product buscarPorId(int id) {
        Product producto = null;
        String sql = "SELECT * FROM Productos.product WHERE id = ?";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                producto = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getString("trade_mark"),
                        resultSet.getInt("stock"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getDate("expiration_date"),
                        resultSet.getString("status").charAt(0), // Agregar estado (A/I)
                        resultSet.getString("code_product") // Agregar código de producto
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    public void eliminar(int id) {
        String sql = "UPDATE Productos.product SET status = 'I' WHERE id = ?"; // Cambiar el estado a inactivo
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate(); // Ejecutar la actualización
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
    }

    public void eliminarDefinitivoPorId(int id) {
        String sql = "DELETE FROM Productos.product WHERE id = ? AND status = 'I'"; // Eliminar solo el producto inactivo con el id específico
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int filasEliminadas = statement.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Producto inactivo con ID " + id + " eliminado permanentemente.");
            } else {
                System.out.println("No se encontró un producto inactivo con el ID " + id + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
    }

    public boolean restaurarProducto(int id) {
        String sql = "UPDATE Productos.product SET status = 'A' WHERE id = ?";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Devuelve true si se actualizó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Devuelve false si hubo un error
    }

    public List<Product> buscarProductosActivos(String query) {
        List<Product> results = new ArrayList<>();
        String sql = "SELECT * FROM Productos.product WHERE (name LIKE ? OR tradeMark LIKE ? OR category LIKE ?) AND status = 'A'";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchPattern = "%" + query + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setCategory(resultSet.getString("category"));
                product.setTradeMark(resultSet.getString("tradeMark"));
                product.setStock(resultSet.getInt("stock"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setExpirationDate(resultSet.getDate("expirationDate"));
                product.setStatus(resultSet.getString("status").charAt(0));
                product.setCodeProduct(resultSet.getString("codeProduct"));
                results.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Para fines de depuración
            // Aquí podrías lanzar una excepción personalizada si lo deseas
        }

        return results;
    }

    public List<Product> buscarProductosInactivos(String query) {
        List<Product> results = new ArrayList<>();
        String sql = "SELECT * FROM Productos.product WHERE (name LIKE ? OR tradeMark LIKE ? OR category LIKE ?) AND status = 'I'";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchPattern = "%" + query + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setCategory(resultSet.getString("category"));
                product.setTradeMark(resultSet.getString("tradeMark"));
                product.setStock(resultSet.getInt("stock"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setExpirationDate(resultSet.getDate("expirationDate"));
                product.setStatus(resultSet.getString("status").charAt(0));
                product.setCodeProduct(resultSet.getString("codeProduct"));
                results.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Para fines de depuración
            // Aquí podrías lanzar una excepción personalizada si lo deseas
        }

        return results;
    }

    public void exportarA_pdf(List<Product> productos, String rutaArchivo) {
        try {
            PdfWriter writer = new PdfWriter(rutaArchivo);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Agregar título
            document.add(new Paragraph("Lista de Productos"));

            // Crear tabla
            Table table = new Table(6); // 6 columnas
            table.addHeaderCell("ID");
            table.addHeaderCell("Nombre");
            table.addHeaderCell("Descripción");
            table.addHeaderCell("Categoría");
            table.addHeaderCell("Marca");
            table.addHeaderCell("Cantidad");
            table.addHeaderCell("Precio");
            table.addHeaderCell("Fecha de Vencimiento");
            table.addHeaderCell("Estado");
            table.addHeaderCell("Código de Producto");

            for (Product producto : productos) {
                table.addCell(String.valueOf(producto.getId()));
                table.addCell(producto.getName());
                table.addCell(producto.getDescription());
                table.addCell(producto.getCategory());
                table.addCell(producto.getTradeMark());
                table.addCell(String.valueOf(producto.getStock()));
                table.addCell(producto.getPrice().toString());
                table.addCell(producto.getExpirationDate().toString());
                table.addCell(String.valueOf(producto.getStatus()));
                table.addCell(producto.getCodeProduct());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportarA_excel(List<Product> productos, String rutaArchivo) {
        Workbook workbook = new XSSFWorkbook(); // Crear un nuevo libro de trabajo
        Sheet sheet = workbook.createSheet("Productos"); // Crear una nueva hoja

        // Crear la fila del encabezado
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nombre");
        headerRow.createCell(2).setCellValue("Descripción");
        headerRow.createCell(3).setCellValue("Categoría");
        headerRow.createCell(4).setCellValue("Marca");
        headerRow.createCell(5).setCellValue("Stock");
        headerRow.createCell(6).setCellValue("Precio");
        headerRow.createCell(7).setCellValue("Fecha de Expiración");
        headerRow.createCell(8).setCellValue("Estado");
        headerRow.createCell(9).setCellValue("Código de Producto");

        // Agregar los datos de los productos
        int rowNum = 1;
        for (Product producto : productos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(producto.getId());
            row.createCell(1).setCellValue(producto.getName());
            row.createCell(2).setCellValue(producto.getDescription());
            row.createCell(3).setCellValue(producto.getCategory());
            row.createCell(4).setCellValue(producto.getTradeMark());
            row.createCell(5).setCellValue(producto.getStock());
            row.createCell(6).setCellValue(producto.getPrice().toString());
            row.createCell(7).setCellValue(producto.getExpirationDate().toString());
            row.createCell(8).setCellValue(String.valueOf(producto.getStatus()));
            row.createCell(9).setCellValue(producto.getCodeProduct());
        }

        // Ajustar el tamaño de las columnas
        for (int i = 0; i < 10; i++) {
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

}

