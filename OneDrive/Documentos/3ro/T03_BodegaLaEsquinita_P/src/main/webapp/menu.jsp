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
            <a href="listarSupplier">Inicio</a>
            <a href="listarProductos">Productos</a>
            <a href="#">Gestión de productos</a>
            <a href="#">Cerrar Sesión</a>
        </div>

        <!-- Main content -->
        <div class="col-md-10 main-content">
            <h3>Lista de Productos</h3>
            <p>Productos por categoría, cantidad, calidad, funciones entre otros</p>

            <!-- Barra de búsqueda y botón agregar -->
            <div class="d-flex justify-content-between mb-3">
                <input type="text" class="form-control w-50" placeholder="Buscar producto...">
                <button class="btn btn-danger">Añadir Producto</button>
            </div>

            <!-- Tabla de productos -->
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Tipo de Producto</th>
                    <th>Nombre</th>
                    <th>Marca</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Limpieza</td>
                    <td>Lejía</td>
                    <td>Clorox</td>
                    <td>70 Unidades</td>
                    <td>$3.00</td>
                    <td>
                        <button class="btn btn-edit">Editar</button>
                        <button class="btn btn-delete">Eliminar</button>
                    </td>
                </tr>
                <tr>
                    <td>Cocina</td>
                    <td>Arroz 1kg</td>
                    <td>Costeño</td>
                    <td>200 Unidades</td>
                    <td>$4.00</td>
                    <td>
                        <button class="btn btn-edit">Editar</button>
                        <button class="btn btn-delete">Eliminar</button>
                    </td>
                </tr>
                <!-- Repite las filas según tu lista de productos -->
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
