<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Clientes Inactivos - La Esquinita</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f5f5f5;
        }
        .sidebar {
            background-color: #d32f2f;
            color: white;
            height: 100vh;
            padding: 20px;
            font-weight: bold;
        }
        .sidebar a {
            color: white;
            text-decoration: none;
            display: block;
            margin: 15px 0;
            font-size: 18px;
        }
        .sidebar a:hover {
            text-decoration: underline;
        }
        .main-content {
            padding: 20px;
            background-color: #f5f5f5;
        }
        .table {
            font-size: 0.9rem;
            border: 1px solid #dee2e6;
        }
        .table th, .table td {
            vertical-align: middle;
            border: 1px solid #dee2e6;
        }
        .btn-restore {
            background-color: #4caf50; /* Verde para restaurar */
            color: white;
        }
        .btn-delete {
            background-color: #d32f2f; /* Rojo para eliminar */
            color: white;
        }
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 sidebar">
            <h2>BODEGA LA ESQUINITA</h2>
            <a href="home.jsp">Inicio</a>
            <a href="listarSupplier">Proveedores</a>
            <a href="listarProductos">Productos</a>
            <a href="#" class="active">Gestión de Productos Inactivos</a>
            <a href="#">Cerrar Sesión</a>
        </div>

        <!-- Main content -->
        <div class="col-md-10 main-content">
            <h3>Lista de Clientes Inactivos</h3>

            <!-- Barra de búsqueda y botón agregar -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div class="form-check form-switch me-1">
                    <input class="form-check-input" type="checkbox" role="switch" id="chkEstado" onclick="listarCustomers()">
                    <label class="form-check-label" for="chkEstado">Mostrar Activos</label>
                </div>
            </div>

            <div class="table-container">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Tipo de Documento</th>
                        <th>Número de Documento</th>
                        <th>Fecha de Nacimiento</th>
                        <th>Teléfono</th>
                        <th>Email</th>
                        <th>Dirección</th>
                        <th>Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="customer" items="${listarCustomersInactivos}">
                        <tr>
                            <td>${customer.name}</td>
                            <td>${customer.lastName}</td>
                            <td>${customer.documentType}</td>
                            <td>${customer.numberDocument}</td>
                            <td>${customer.birthdate}</td>
                            <td>${customer.phone}</td>
                            <td>${customer.email}</td>
                            <td>${customer.address}</td>
                            <td>
                                <a href="restaurarCustomer?id=${customer.id}" class="btn btn-primary btn-sm">Restaurar</a>
                                <a href="eliminarCustomerInactivoPorId?id=${customer.id}" class="btn btn-danger btn-sm">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    function listarCustomers() {
        var estado = document.getElementById('chkEstado').checked;

        if (estado) {
            window.location.href = 'listarCustomers'; // URL para clientes activos
        } else {
            window.location.href = 'listarCustomersInactivos'; // Asegúrate de que esta URL sea correcta
        }
    }

</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
