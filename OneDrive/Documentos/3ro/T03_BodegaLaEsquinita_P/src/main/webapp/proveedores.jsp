<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Proveedores - La Esquinita</title>
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
            padding: 40px;
            background-color: #f5f5f5;
        }
        .table th, .table td {
            vertical-align: middle;
        }
        .btn-edit {
            background-color: #4a90e2;
            color: white;
        }
        .btn-delete {
            background-color: #d32f2f;
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
            <a href="#">Gestión de productos</a>
            <a href="#">Cerrar Sesión</a>
        </div>


        <div class="col-md-10 main-content">
            <h3>Lista de Proveedores</h3>
            <p>Información sobre proveedores, contacto, dirección y más.</p>


            <div class="d-flex justify-content-between mb-3">
                <input type="text" class="form-control w-50" placeholder="Buscar proveedor...">
                <a href="agregarProveedor.jsp" class="btn btn-danger">Añadir Proveedor</a>
            </div>


            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Dirección</th>
                    <th>Correo Electrónico</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="supplier" items="${supplierList}">
                    <tr>
                        <td>${supplier.id}</td>
                        <td>${supplier.name}</td>
                        <td>${supplier.direction}</td>
                        <td>${supplier.email}</td>
                        <td>
                            <a href="editarSupplier?id=${supplier.id}" class="btn btn-primary">Editar</a>
                            <a href="eliminarSupplier?id=${supplier.id}" class="btn btn-danger">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
