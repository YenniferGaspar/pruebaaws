package pe.edu.vallegrande.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros del formulario de inicio de sesión
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Aquí deberías verificar el usuario en la base de datos
        if ("admin".equals(username) && "1234".equals(password)) {
            // Redirigir al home si el usuario es correcto
            response.sendRedirect("home.jsp");
        } else {
            // Redirigir al login con un parámetro de error si el usuario es incorrecto
            response.sendRedirect("home.jsp?error=true");
        }
    }
}
