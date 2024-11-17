<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Productos - La Esquinita</title>
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
        <!-- Sidebar -->
        <div class="col-md-2 sidebar">
            <h2>BODEGA LA ESQUINITA</h2>
            <a href="home.jsp"><i class="bi bi-house-door-fill"></i> Inicio</a>
            <a href="listarSupplier">Proveedores</a>
            <a href="listarProductos"><i class="bi bi-box-seam"></i> Productos</a>
            <a href="listarCustomers"><i class="bi bi-list-check"></i> Clientes</a>
            <a href="#"> Gestión de Inventario</a>
            <a href="#"> Cerrar Sesión</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

