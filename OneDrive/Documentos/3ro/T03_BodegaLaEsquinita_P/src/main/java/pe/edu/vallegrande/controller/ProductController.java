package pe.edu.vallegrande.controller;

import pe.edu.vallegrande.model.Product;
import pe.edu.vallegrande.service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;

import com.google.gson.Gson;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet({"/editarProduct", "/agregarProduct", "/eliminarProduct", "/listarProductos", "/listarProductosInactivos", "/restaurarProduct", "/eliminarProductoInactivoPorId", "/buscarProductos", "/buscarProductosInactivos", "/exportarProductos", "/exportarProductosExcel"})
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService = new ProductService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/editarProduct":
                editarProduct(request, response);
                break;
            case "/agregarProduct":
                agregarProduct(request, response);
                break;
            case "/eliminarProduct": // Añadir la ruta para eliminar productos
                eliminarProduct(request, response);
                break;
            case "/eliminarProductoInactivoPorId": // Añadir la ruta para eliminar productos
                eliminarProductoInactivoPorId(request, response);
                break;
            case "/restaurarProduct": // Añadir la ruta para restaurar productos
                restaurarProducto(request, response);
                break;
            case "/listarProductos":
                listarProductos(request, response);
                break;
            case "/listarProductosInactivos":
                listarProductosInactivos(request, response);
                break;
            case "/buscarProductos":
                buscarProductos(request, response);
                break;
            case "/buscarProductosInactivos":
                buscarProductosInactivos(request, response);
                break;
            case "/exportarProductos":
                exportarProductos(request, response);
                break;
            case "/exportarProductosExcel":
                exportarProductosExcel(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    protected void editarProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Product producto = productService.buscarPorId(id);

            // Formatear la fecha de vencimiento en el formato correcto
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(producto.getExpirationDate());

            request.setAttribute("producto", producto);
            request.setAttribute("formattedDate", formattedDate);
            RequestDispatcher rd = request.getRequestDispatcher("editarProducto.jsp");
            rd.forward(request, response);
        } else if (request.getMethod().equalsIgnoreCase("POST")) {
            request.setCharacterEncoding("UTF-8");
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String category = request.getParameter("category");
                String tradeMark = request.getParameter("trade_mark");

                // Obtener y convertir el stock
                String stockString = request.getParameter("stock");
                int stock = (stockString != null && !stockString.isEmpty()) ? Integer.parseInt(stockString) : 0;

                // Obtener y convertir el precio
                String priceString = request.getParameter("price");
                BigDecimal price = (priceString != null && !priceString.isEmpty()) ? new BigDecimal(priceString) : null;

                // Obtener la fecha en formato yyyy-MM-dd
                String fechaCadena = request.getParameter("expiry_date");
                java.sql.Date expirationDate = null;

                // Validar y convertir la fecha
                if (fechaCadena != null && !fechaCadena.isEmpty()) {
                    SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date fechaUtil = formatoEntrada.parse(fechaCadena);
                    expirationDate = new java.sql.Date(fechaUtil.getTime());
                }

                // Estado por defecto (por ejemplo, 'A' para Activo)
                String status = "A";
                String codeProduct = request.getParameter("code_product");

                // Crear el objeto Product con el estado predeterminado
                Product producto = new Product(id, name, description, category, tradeMark, stock, price, expirationDate, status.charAt(0), codeProduct);

                // Editar el producto
                productService.editar(producto);
                response.sendRedirect("listarProductos");
            } catch (ParseException e) {
                e.printStackTrace();
                request.setAttribute("error", "Fecha no válida: debe estar en el formato yyyy-MM-dd");
                request.getRequestDispatcher("editarProducto.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("error", "Por favor, asegúrese de que los campos numéricos son válidos.");
                request.getRequestDispatcher("editarProducto.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error al editar el producto: " + e.getMessage());
                request.getRequestDispatcher("editarProducto.jsp").forward(request, response);
            }
        }
    }


    protected void agregarProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String tradeMark = request.getParameter("trade_mark");
        String category = request.getParameter("category");
        String codeProduct = request.getParameter("code_product");
        String stockString = request.getParameter("stock");
        String priceString = request.getParameter("price");
        String fechaCadena = request.getParameter("expiry_date");

        // Para mantener los datos en caso de error
        request.setAttribute("name", name);
        request.setAttribute("description", description);
        request.setAttribute("trade_mark", tradeMark);
        request.setAttribute("category", category);
        request.setAttribute("code_product", codeProduct);
        request.setAttribute("stock", stockString);
        request.setAttribute("price", priceString);
        request.setAttribute("expiry_date", fechaCadena);

        List<String> errors = new ArrayList<>();

        // Validación del nombre (solo letras)
        if (name == null || name.trim().isEmpty() || !name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            errors.add("El nombre solo debe contener letras.");
        }

        // Validación de descripción (puedes añadir restricciones si lo deseas, por ejemplo, longitud mínima)
        if (description == null || description.trim().isEmpty()) {
            errors.add("La descripción es obligatoria.");
        }

        // Validación de stock
        int stock = 0;
        if (stockString == null || stockString.trim().isEmpty()) {
            errors.add("El stock es obligatorio.");
        } else {
            try {
                stock = Integer.parseInt(stockString);
                if (stock < 0) {
                    errors.add("El stock no puede ser menor a 0.");
                }
            } catch (NumberFormatException e) {
                errors.add("El stock debe ser un número válido.");
            }
        }

        // Validación de precio
        BigDecimal price = null;
        if (priceString == null || priceString.trim().isEmpty()) {
            errors.add("El precio es obligatorio.");
        } else {
            try {
                price = new BigDecimal(priceString);
                if (price.compareTo(BigDecimal.ZERO) < 0) {
                    errors.add("El precio no puede ser menor a 0.");
                }
            } catch (NumberFormatException e) {
                errors.add("El precio debe ser un número válido.");
            }
        }

        // Validación de fecha de vencimiento
        java.sql.Date expirationDate = null;
        if (fechaCadena == null || fechaCadena.trim().isEmpty()) {
            errors.add("La fecha de vencimiento es obligatoria.");
        } else {
            try {
                expirationDate = java.sql.Date.valueOf(fechaCadena);
                java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
                java.sql.Date tenMonthsLater = java.sql.Date.valueOf(today.toLocalDate().plusMonths(10));

                if (expirationDate.before(tenMonthsLater)) {
                    errors.add("La fecha de vencimiento debe ser al menos 10 meses en el futuro.");
                }
            } catch (IllegalArgumentException e) {
                errors.add("Formato de fecha no válido. Debe ser en formato yyyy-mm-dd.");
            }
        }

        // Si hay errores, mostrar el formulario con los errores
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("agregarProducto.jsp").forward(request, response);
            return;
        }

        // Crear el objeto Product con estado 'A' por defecto
        Product producto = new Product(0, name, description, category, tradeMark, stock, price, expirationDate, 'A', codeProduct);

        // Intentar agregar el producto
        try {
            int resultado = productService.agregar(producto);

            if (resultado > 0) {
                response.sendRedirect("listarProductos");
            } else {
                request.setAttribute("error", "Error al agregar el producto. Intente nuevamente.");
                request.getRequestDispatcher("agregarProducto.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Manejo de excepciones específicas
            if (e.getMessage().contains("Violation of UNIQUE KEY constraint")) {
                request.setAttribute("error", "El código del producto ya está en uso. Por favor, ingrese uno diferente.");
                request.getRequestDispatcher("agregarProducto.jsp").forward(request, response);
            }else {
                e.printStackTrace();
                request.setAttribute("error", "Error inesperado en la base de datos.");
                request.getRequestDispatcher("agregarProducto.jsp").forward(request, response);
            }
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el parámetro de consulta para ver si se deben mostrar los inactivos
        String mostrarInactivosParam = request.getParameter("inactivos");
        boolean mostrarInactivos = mostrarInactivosParam != null && mostrarInactivosParam.equals("true");

        List<Product> productListar;

        if (mostrarInactivos) {
            // Lógica para listar productos inactivos
            productListar = productService.listarInactivos(); // Asegúrate de tener este método en tu servicio
        } else {
            // Lógica para listar productos activos
            productListar = productService.listarActivos(); // Asegúrate de tener este método en tu servicio
            request.setCharacterEncoding("UTF-8");
        }

        List<Product> formattedProductList = new ArrayList<>();

        for (Product product : productListar) {
            Product formattedProduct = new Product();
            formattedProduct.setId(product.getId());
            formattedProduct.setName(product.getName());
            formattedProduct.setDescription(product.getDescription());
            formattedProduct.setCategory(product.getCategory());
            formattedProduct.setTradeMark(product.getTradeMark());
            formattedProduct.setStock(product.getStock());
            formattedProduct.setPrice(product.getPrice());
            formattedProduct.setExpirationDate(product.getExpirationDate());
            formattedProduct.setStatus(product.getStatus());
            formattedProduct.setCodeProduct(product.getCodeProduct());

            formattedProductList.add(formattedProduct);
        }

        request.setAttribute("productListar", formattedProductList);
        RequestDispatcher rd = request.getRequestDispatcher("productos.jsp");
        rd.forward(request, response);
    }

    private void listarProductosInactivos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> productListar = productService.listarInactivos(); // Método que debe implementar en ProductService
        List<Product> formattedProductList = new ArrayList<>();

        for (Product product : productListar) {
            Product formattedProduct = new Product();
            formattedProduct.setId(product.getId());
            formattedProduct.setName(product.getName());
            formattedProduct.setDescription(product.getDescription());
            formattedProduct.setCategory(product.getCategory());
            formattedProduct.setTradeMark(product.getTradeMark());
            formattedProduct.setStock(product.getStock());
            formattedProduct.setPrice(product.getPrice());
            formattedProduct.setExpirationDate(product.getExpirationDate());
            formattedProduct.setStatus(product.getStatus());
            formattedProduct.setCodeProduct(product.getCodeProduct());

            formattedProductList.add(formattedProduct);
        }

        request.setAttribute("listarProductosInactivos", formattedProductList);
        RequestDispatcher rd = request.getRequestDispatcher("productosInactivos.jsp"); // Puedes usar un JSP diferente para inactivos
        rd.forward(request, response);
    }

    private void eliminarProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id")); // Obtener el ID del producto a eliminar
        productService.eliminar(id); // Asegúrate de que este método actualice el estado a inactivo
        response.sendRedirect("listarProductos"); // Redirigir a la lista de productos
    }

    private void eliminarProductoInactivoPorId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID del producto de los parámetros de la solicitud
            int id = Integer.parseInt(request.getParameter("id"));

            // Llamar al servicio para eliminar el producto inactivo con el ID especificado
            productService.eliminarDefinitivoPorId(id);

            // Redirigir a la lista de productos
            response.sendRedirect("listarProductosInactivos");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de producto inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar el producto.");
        }
    }

    private void restaurarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean restaurado = productService.restaurarProducto(id);

        if (restaurado) {
            request.setAttribute("mensaje", "Producto restaurado exitosamente.");
        } else {
            request.setAttribute("mensaje", "Error al restaurar el producto.");
        }

        // Redirigir nuevamente a la lista de productos inactivos
        listarProductosInactivos(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("buscar".equals(action)) {
            buscarProductos(request, response);
        }
    }
    private void buscarProductos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = request.getParameter("query");
        List<Product> productosBuscados = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            productosBuscados = productService.buscarProductosActivos(query);
        }

        // Convertir la lista de productos a JSON
        String json = new Gson().toJson(productosBuscados);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    private void buscarProductosInactivos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = request.getParameter("query");
        List<Product> productosBuscados = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            productosBuscados = productService.buscarProductosInactivos(query);
        }

        // Convertir la lista de productos a JSON
        String json = new Gson().toJson(productosBuscados);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    private void exportarProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> productos = productService.listarActivos(); // Obtiene la lista de productos activos

        // Obtener la ruta de la carpeta Descargas del usuario
        String userHome = System.getProperty("user.home");
        String rutaArchivo = userHome + File.separator + "Downloads" + File.separator + "Productos.pdf"; // Ruta donde guardarás el PDF

        try {
            // Llama al método para exportar a PDF
            productService.exportarA_pdf(productos, rutaArchivo);

            // Configuración de la respuesta
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Productos.pdf");

            // Leer el archivo PDF y escribirlo en el OutputStream de la respuesta
            Files.copy(Paths.get(rutaArchivo), response.getOutputStream());
            response.getOutputStream().flush();

            // Opcional: Eliminar el archivo PDF después de enviarlo
            // Files.delete(Paths.get(rutaArchivo)); // Descomentar si quieres eliminar el archivo después de enviarlo

        } catch (IOException e) {
            e.printStackTrace(); // Log del error
            request.setAttribute("error", "Error al generar el PDF: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Captura cualquier otra excepción
            request.setAttribute("error", "Ocurrió un error inesperado: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    private void exportarProductosExcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> productos = productService.listarActivos(); // Asegúrate de que este método retorna la lista de productos activos

        // Obtener la ruta de la carpeta Descargas del usuario
        String userHome = System.getProperty("user.home");
        String rutaArchivo = userHome + File.separator + "Downloads" + File.separator + "Productos.xlsx"; // Define la ruta donde guardarás el Excel

        try {
            productService.exportarA_excel(productos, rutaArchivo); // Llama al método para exportar a Excel
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido para archivos Excel
            response.setHeader("Content-Disposition", "attachment; filename=Productos.xlsx"); // Nombre del archivo al descargar
            response.getOutputStream().write(Files.readAllBytes(Paths.get(rutaArchivo))); // Envía el Excel al cliente
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al generar el Excel: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}



