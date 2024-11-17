<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        .btn-edit {
            background-color: #4a90e2;
            color: white;
        }
        .btn-delete {
            background-color: #d32f2f;
            color: white;
        }
        .inactivo {
            opacity: 0.5;
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
            <a href="#">Gestión de Inventario</a>
            <a href="#">Cerrar Sesión</a>
        </div>

        <!-- Main content -->
        <div class="col-md-10 main-content">
            <h3>Lista de Productos</h3>
            <p>Productos por categoría, cantidad, calidad, funciones entre otros</p>

            <!-- Barra de búsqueda, checkbox y botón agregar -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <input type="text" id="buscar" class="form-control w-50 me-2" placeholder="Buscar producto por ID, nombre o categoría" aria-label="Buscar producto" onkeypress="if(event.key === 'Enter'){ buscarProducto(); }">
                <div class="form-check form-switch me-1">
                    <input class="form-check-input" type="checkbox" role="switch" id="chkEstado" onclick="listarProductosInactivos()">
                    <label class="form-check-label" for="chkEstado">Mostrar Inactivos</label>
                </div>
                <a href="agregarProducto.jsp" class="btn btn-danger">Añadir Producto</a>
                <a href="exportarProductosExcel" class="btn btn-info">Exportar Clientes a Excel</a>
                <a href="exportarProductos" class="btn btn-success">Exportar Clientes a PDF</a>
            </div>

            <!-- Tabla de productos -->
            <table class="table table-striped">
                <thead>
                <tr>
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
                <c:forEach var="producto" items="${productListar}">
                    <tr>
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
                            <a href="editarProduct?id=${producto.id}" class="btn btn-primary">Editar</a>
                            <a href="eliminarProduct?id=${producto.id}" class="btn btn-danger">Eliminar</a>
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
    function buscarProducto() {
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

    function listarProductosInactivos() {
        var estado = document.getElementById('chkEstado').checked;

        if (estado) {
            window.location.href = 'listarProductosInactivos';
        } else {
            window.location.href = 'productos.jsp'; // Cambia esto si es necesario
        }
    }
</script>
</body>
</html>
