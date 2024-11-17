<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Productos Inactivos - La Esquinita</title>
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
            <h3>Lista de Productos Inactivos</h3>
            <p>Gestión de productos que no están actualmente activos en la bodega.</p>

            <!-- Barra de búsqueda y botón agregar -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <input type="text" id="buscar" class="form-control w-50 me-2" placeholder="Buscar producto por ID, nombre o categoría" aria-label="Buscar producto" onkeypress="if(event.key === 'Enter'){ buscarProductoInactivo(); }">
                <div class="form-check form-switch me-1">
                    <input class="form-check-input" type="checkbox" role="switch" id="chkEstado" onclick="listarProductos()">
                    <label class="form-check-label" for="chkEstado">Mostrar Activos</label>
                </div>
            </div>

            <!-- Tabla de productos inactivos -->
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Categoría</th>
                    <th>Marca</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                    <th>Fecha de Vencimiento</th>
                    <th>Estado</th>
                    <th>Código de Producto</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody id="productTableBody">
                <c:forEach var="producto" items="${listarProductosInactivos}">
                    <tr>
                        <td>${producto.id}</td>
                        <td>${producto.name}</td>
                        <td>${producto.description}</td>
                        <td>${producto.category}</td>
                        <td>${producto.tradeMark}</td>
                        <td>${producto.stock}</td>
                        <td>${producto.price}</td>
                        <td>${producto.expirationDate}</td>
                        <td>${producto.status}</td>
                        <td>${producto.codeProduct}</td>
                        <td>
                            <a href="restaurarProduct?id=${producto.id}" class="btn btn-restore">Restaurar</a>
                            <a href="eliminarProductoInactivoPorId?id=${producto.id}" class="btn btn-delete">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>

    function buscarProductoInactivo() {
        const query = document.getElementById('buscar').value.toLowerCase();
        const rows = document.querySelectorAll('#productTableBody tr');

        rows.forEach(row => {
            const cells = row.querySelectorAll('td');
            const found = Array.from(cells).some(cell =>
                cell.textContent.toLowerCase().includes(query)
            );
            row.style.display = found ? '' : 'none';
        });

        if (query.trim() === "") {
            // Si no hay búsqueda, mostrar todas las filas
            rows.forEach(row => row.style.display = '');
        }
    }


    function listarProductos() {
        // Obtén el estado del checkbox
        var estado = document.getElementById('chkEstado').checked;

        // Si el checkbox está marcado, redirige a productosInactivos.jsp
        if (estado) {
            window.location.href = 'listarProductos';
        } else {
            // Si deseas redirigir de nuevo a la lista de productos activos, puedes agregar esa lógica aquí
            window.location.href = 'productosInactivos.jsp'; // Cambia esto si es necesario
        }
    }
</script>
</body>
</html>
