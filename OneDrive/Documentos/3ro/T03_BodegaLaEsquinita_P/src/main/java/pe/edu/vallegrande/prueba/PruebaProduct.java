package pe.edu.vallegrande.prueba;

import pe.edu.vallegrande.service.ProductService;
import pe.edu.vallegrande.model.Product;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class PruebaProduct {

    public static void main(String[] args) {
        PruebaProduct prueba = new PruebaProduct();

        // Pruebas de los métodos de ProductService
        prueba.pruebaAgregarProducto();
        prueba.pruebaListarActivos();
        prueba.pruebaListarInactivos();
        prueba.pruebaBuscarPorId();
        prueba.pruebaEditar();
        prueba.pruebaEliminar();
        prueba.pruebaEliminarDefinitivo();
        prueba.pruebaRestaurarProducto();
    }

    // Prueba de agregar un producto
    public void pruebaAgregarProducto() {
        ProductService productService = new ProductService();
        Product producto = new Product(1, "Producto A", "Descripción A", "Categoría A", "Marca A", 10, BigDecimal.valueOf(100.0), Date.valueOf("2025-12-31"), 'A', "P001");

        int filasAfectadas = productService.agregar(producto);
        System.out.println("Filas afectadas al agregar producto: " + filasAfectadas);
    }

    // Prueba de listar productos activos
    public void pruebaListarActivos() {
        ProductService productService = new ProductService();
        List<Product> productos = productService.listarActivos();
        System.out.println("Productos activos:");
        for (Product producto : productos) {
            System.out.println(producto.getName());
        }
    }

    // Prueba de listar productos inactivos
    public void pruebaListarInactivos() {
        ProductService productService = new ProductService();
        List<Product> productos = productService.listarInactivos();
        System.out.println("Productos inactivos:");
        for (Product producto : productos) {
            System.out.println(producto.getName());
        }
    }

    // Prueba de buscar un producto por ID
    public void pruebaBuscarPorId() {
        ProductService productService = new ProductService();
        Product producto = productService.buscarPorId(1); // Cambiar 1 por un ID que exista
        if (producto != null) {
            System.out.println("Producto encontrado: " + producto.getName());
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    // Prueba de editar un producto
    public void pruebaEditar() {
        ProductService productService = new ProductService();
        Product producto = new Product(1, "Producto A Modificado", "Descripción A", "Categoría A", "Marca A", 15, BigDecimal.valueOf(120.0), Date.valueOf("2025-12-31"), 'A', "P001");

        int filasAfectadas = productService.editar(producto);
        System.out.println("Filas afectadas al editar producto: " + filasAfectadas);
    }

    // Prueba de eliminar un producto (cambiar estado a inactivo)
    public void pruebaEliminar() {
        ProductService productService = new ProductService();
        productService.eliminar(1); // Cambiar 1 por un ID que exista
        System.out.println("Producto marcado como inactivo.");
    }

    // Prueba de eliminar un producto definitivamente
    public void pruebaEliminarDefinitivo() {
        ProductService productService = new ProductService();
        productService.eliminarDefinitivoPorId(1); // Cambiar 1 por un ID que esté inactivo
    }

    // Prueba de restaurar un producto
    public void pruebaRestaurarProducto() {
        ProductService productService = new ProductService();
        boolean restaurado = productService.restaurarProducto(1); // Cambiar 1 por un ID que esté inactivo
        if (restaurado) {
            System.out.println("Producto restaurado exitosamente.");
        } else {
            System.out.println("No se pudo restaurar el producto.");
        }
    }
}
