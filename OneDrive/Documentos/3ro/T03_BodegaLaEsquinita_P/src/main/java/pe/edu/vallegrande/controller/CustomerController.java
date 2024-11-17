package pe.edu.vallegrande.controller;

import pe.edu.vallegrande.model.Customer;

import pe.edu.vallegrande.service.CustomerService; // Cambiar a "service"

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.IOException;
import java.util.List;
import java.sql.Date;
import java.util.ArrayList;
import com.google.gson.Gson;


@WebServlet({"/editarCustomer", "/agregarCustomer", "/eliminarCustomer", "/listarCustomers", "/listarCustomersInactivos", "/eliminarCustomerInactivoPorId", "/restaurarCustomer", "/exportarClientes", "/exportarClientesExcel","/buscarClientes" })
public class CustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerService customerService = new CustomerService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/editarCustomer":
                editarCustomer(request, response);
                break;
            case "/agregarCustomer":
                agregarCustomer(request, response);
                break;
            case "/eliminarCustomer":
                eliminarCustomer(request, response);
                break;
            case "/eliminarCustomerInactivoPorId":
                eliminarCustomerInactivoPorId(request, response);
                break;
            case "/listarCustomers":
                listarCustomers(request, response);
                break;
            case "/listarCustomersInactivos":
                listarCustomersInactivos(request, response);
                break;
            case "/restaurarCustomer":
                restaurarCustomer(request, response);
                break;
            case "/exportarClientes":
                exportarClientes(request, response);
                break;
            case "/exportarClientesExcel":
                exportarClientesExcel(request, response);
                break;
            case "/buscarClientes":
                exportarClientesExcel(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    protected void editarCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Customer customer = customerService.buscarPorId(id);

            // Formatear la fecha de nacimiento si es necesario
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedBirthdate = sdf.format(customer.getBirthdate());

            request.setAttribute("customer", customer);
            request.setAttribute("formattedBirthdate", formattedBirthdate); // Asegúrate de que esto se pasa correctamente
            RequestDispatcher rd = request.getRequestDispatcher("editarCustomer.jsp");
            rd.forward(request, response);
        } else if (request.getMethod().equalsIgnoreCase("POST")) {
            request.setCharacterEncoding("UTF-8");
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String lastName = request.getParameter("last_name");
                String documentType = request.getParameter("document_type");
                String documentNumber = request.getParameter("number_document");
                String mobile = request.getParameter("phone");
                String email = request.getParameter("email");
                String address = request.getParameter("address");
                char status = request.getParameter("status").charAt(0);

                // Validar que el estado sea 'A' o 'I'
                if (status != 'A' && status != 'I') {
                    throw new IllegalArgumentException("El estado debe ser 'A' (Activo) o 'I' (Inactivo).");
                }

                // Obtener y validar la fecha de nacimiento
                String birthdateString = request.getParameter("birthdate");
                java.sql.Date birthdate = null;
                if (birthdateString != null && !birthdateString.isEmpty()) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date utilDate = format.parse(birthdateString);
                    birthdate = new java.sql.Date(utilDate.getTime());
                } else {
                    request.setAttribute("error", "Fecha de nacimiento no válida. Debe ser seleccionada.");
                    request.getRequestDispatcher("editarCustomer.jsp").forward(request, response);
                    return;
                }

                // Crear el objeto Customer
                Customer customer = new Customer(id, name, lastName, documentType, documentNumber, birthdate, mobile, email, address, status);

                // Editar el cliente
                customerService.editar(customer);
                response.sendRedirect("listarCustomers");
            } catch (ParseException e) {
                e.printStackTrace();
                request.setAttribute("error", "Fecha no válida: debe estar en el formato yyyy-MM-dd");
                request.getRequestDispatcher("editarCustomer.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("error", "Por favor, asegúrese de que los campos numéricos son válidos.");
                request.getRequestDispatcher("editarCustomer.jsp").forward(request, response);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("editarCustomer.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error al editar el cliente: " + e.getMessage());
                request.getRequestDispatcher("editarCustomer.jsp").forward(request, response);
            }
        }
    }

    protected void agregarCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String lastName = request.getParameter("last_name");
        String documentType = request.getParameter("document_type");
        String documentNumber = request.getParameter("number_document");
        String mobile = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address"); // Nuevo campo
        String birthdateString = request.getParameter("birthdate"); // Nuevo campo

        // Convertir birthdate a java.sql.Date
        Date birthdate = null;
        if (birthdateString != null && !birthdateString.isEmpty()) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = format.parse(birthdateString);
                birthdate = new java.sql.Date(utilDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
                request.setAttribute("error", "Fecha de nacimiento no válida.");
                request.getRequestDispatcher("agregarCustomer.jsp").forward(request, response);
                return;
            }
        }

        // Validar y agregar el cliente
        char status = 'A'; // Estado inicial
        Customer customer = new Customer(0, name, lastName, documentType, documentNumber, birthdate, mobile, email, address, status);

        try {
            int resultado = customerService.agregar(customer);
            if (resultado > 0) {
                response.sendRedirect("listarCustomers");
            } else {
                request.setAttribute("error", "Error al agregar el cliente. Intente nuevamente.");
                request.getRequestDispatcher("agregarCustomer.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en la base de datos: " + e.getMessage());
            request.getRequestDispatcher("agregarCustomer.jsp").forward(request, response);
        }
    }

    private void listarCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customerListar = customerService.listarActivos(); // Asegúrate de que este método esté implementado en CustomerService
        System.out.println("Número de clientes activos: " + customerListar.size()); // Debugging
        List<Customer> formattedCustomerList = new ArrayList<>();

        for (Customer customer : customerListar) {
            Customer formattedCustomer = new Customer();
            formattedCustomer.setId(customer.getId());
            formattedCustomer.setName(customer.getName());
            formattedCustomer.setLastName(customer.getLastName());
            formattedCustomer.setDocumentType(customer.getDocumentType());
            formattedCustomer.setNumberDocument(customer.getNumberDocument());
            formattedCustomer.setBirthdate(customer.getBirthdate());
            formattedCustomer.setPhone(customer.getPhone());
            formattedCustomer.setEmail(customer.getEmail());
            formattedCustomer.setAddress(customer.getAddress());
            formattedCustomer.setStatus(customer.getStatus());

            formattedCustomerList.add(formattedCustomer);
        }

        request.setAttribute("listarCustomers", formattedCustomerList);// Cambia el nombre del atributo según tu uso
        RequestDispatcher rd = request.getRequestDispatcher("customer.jsp"); // Asegúrate de que sea el nombre correcto
        rd.forward(request, response);
    }


    private void listarCustomersInactivos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customerListar = customerService.listarInactivos(); // Método que debe implementar en CustomerService
        System.out.println("Número de clientes inactivos: " + customerListar.size()); // Debugging
        List<Customer> formattedCustomerList = new ArrayList<>();

        for (Customer customer : customerListar) {
            Customer formattedCustomer = new Customer();
            formattedCustomer.setId(customer.getId());
            formattedCustomer.setName(customer.getName());
            formattedCustomer.setLastName(customer.getLastName());
            formattedCustomer.setDocumentType(customer.getDocumentType());
            formattedCustomer.setNumberDocument(customer.getNumberDocument());
            formattedCustomer.setBirthdate(customer.getBirthdate());
            formattedCustomer.setPhone(customer.getPhone());
            formattedCustomer.setEmail(customer.getEmail());
            formattedCustomer.setAddress(customer.getAddress());
            formattedCustomer.setStatus(customer.getStatus());

            formattedCustomerList.add(formattedCustomer);
        }

        request.setAttribute("listarCustomersInactivos", formattedCustomerList);
        RequestDispatcher rd = request.getRequestDispatcher("customersInactivos.jsp"); // Asegúrate de usar el nombre correcto
        rd.forward(request, response);
    }

    private void eliminarCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        customerService.eliminar(id);
        response.sendRedirect("listarCustomers");
    }

    private void eliminarCustomerInactivoPorId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID del cliente de los parámetros de la solicitud
            int id = Integer.parseInt(request.getParameter("id"));

            // Llamar al servicio para eliminar el cliente inactivo con el ID especificado
            customerService.eliminarDefinitivoPorId(id);

            // Redirigir a la lista de clientes inactivos
            response.sendRedirect("listarCustomersInactivos");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de cliente inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar el cliente.");
        }
    }

    private void restaurarCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean restaurado = customerService.restaurarCustomer(id); // Método para restaurar el cliente

        if (restaurado) {
            request.setAttribute("mensaje", "Cliente restaurado exitosamente.");
        } else {
            request.setAttribute("mensaje", "Error al restaurar el cliente.");
        }

        // Redirigir nuevamente a la lista de clientes inactivos
        listarCustomersInactivos(request, response);
    }
    private void exportarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> clientes = customerService.listarActivos(); // Obtiene la lista de clientes activos

        // Obtener la ruta de la carpeta Descargas del usuario
        String userHome = System.getProperty("user.home");
        String rutaArchivo = userHome + File.separator + "Downloads" + File.separator + "Clientes.pdf"; // Define la ruta donde guardarás el PDF

        try {
            customerService.exportarA_pdf(clientes, rutaArchivo); // Llama al método para exportar a PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Clientes.pdf");
            response.getOutputStream().write(Files.readAllBytes(Paths.get(rutaArchivo))); // Envía el PDF al cliente
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al generar el PDF: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    private void exportarClientesExcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> clientes = customerService.listarActivos(); // Obtiene la lista de clientes activos

        // Obtener la ruta de la carpeta Descargas del usuario
        String userHome = System.getProperty("user.home");
        String rutaArchivo = userHome + File.separator + "Downloads" + File.separator + "Clientes.xlsx"; // Define la ruta donde guardarás el Excel

        try {
            customerService.exportarA_excel(clientes, rutaArchivo); // Llama al método para exportar a Excel
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Tipo de contenido para archivos Excel
            response.setHeader("Content-Disposition", "attachment; filename=Clientes.xlsx"); // Nombre del archivo al descargar
            response.getOutputStream().write(Files.readAllBytes(Paths.get(rutaArchivo))); // Envía el Excel al cliente
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al generar el Excel: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("buscar".equals(action)) {
            buscarClientes(request, response);
        }
    }

    private void buscarClientes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = request.getParameter("query");
        List<Customer> clientesBuscados = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            clientesBuscados = customerService.buscarClientesActivos(query);
        }

        // Convertir la lista de clientes a JSON
        String json = new Gson().toJson(clientesBuscados);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }



}
