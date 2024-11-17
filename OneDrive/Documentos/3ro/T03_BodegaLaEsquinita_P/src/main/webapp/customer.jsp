<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Clientes - La Esquinita</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.25/jspdf.plugin.autotable.min.js"></script>
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
            <h3>Lista de Clientes</h3>
            <!-- Barra de búsqueda, checkbox y botón agregar -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <input type="text" id="buscar" class="form-control w-50 me-2" placeholder="Buscar producto por ID, nombre o categoría" aria-label="Buscar producto" onkeypress="if(event.key === 'Enter'){ buscarCliente(); }">
                <div class="form-check form-switch me-1">
                    <input class="form-check-input" type="checkbox" role="switch" id="chkEstado" onclick="listarCustomersInactivos()">
                    <label class="form-check-label" for="chkEstado">Mostrar Inactivos</label>
                </div>
                <a href="agregarCustomer.jsp" class="btn btn-danger">Añadir Cliente</a>
                <button onclick="exportTableToExcel('customerTable', 'Clientes')" class="btn btn-info">Exportar a Excel</button>
                <button onclick="exportTableToPDF()" class="btn btn-success">Exportar a PDF</button>
            </div>
            <!-- Tabla -->
            <div class="table-container">
                <table class="table table-striped" id="customerTable">
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
                    <tbody id="customerTableBody">
                    <c:forEach var="customer" items="${listarCustomers}">
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
                                <a href="editarCustomer?id=${customer.id}" class="btn btn-primary btn-sm">Editar</a>
                                <a href="eliminarCustomer?id=${customer.id}" class="btn btn-danger btn-sm">Eliminar</a>
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
    // Exporta la tabla a un archivo Excel
    function exportTableToExcel(tableID, filename = '') {
        let table = document.getElementById(tableID);
        let wb = XLSX.utils.table_to_book(table, { sheet: "Sheet1" });
        XLSX.writeFile(wb, filename ? filename + '.xlsx' : 'Clientes.xlsx');
    }

    // Exporta la tabla a un archivo PDF
    function exportTableToPDF() {
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        doc.autoTable({
            html: '#customerTable',
            startY: 20,
            headStyles: { fillColor: [0, 0, 0] },
            margin: { top: 10 },
            styles: { fontSize: 6 }
        });

        doc.save('Clientes.pdf');
    }

    function listarCustomersInactivos() {
        var estado = document.getElementById('chkEstado').checked;
        if (estado) {
            window.location.href = 'listarCustomersInactivos'; // URL para clientes activos
        } else {
            window.location.href = 'listarCustomers'; // Asegúrate de que esta URL sea correcta
        }
    }
    function buscarCliente() {
        const query = document.getElementById('buscar').value.toLowerCase();
        const rows = document.querySelectorAll('#customerTableBody tr');

        rows.forEach(row => {
            const name = row.cells[1].textContent.toLowerCase(); // Nombre
            const lastName = row.cells[2].textContent.toLowerCase(); // Apellido
            const numberDocument = row.cells[4].textContent.toLowerCase(); // Número de Documento

            // Verifica si el query está en el nombre, apellido o número de documento
            const found = name.includes(query) || lastName.includes(query) || numberDocument.includes(query);
            row.style.display = found ? '' : 'none';
        });

        // Si la consulta está vacía, muestra todas las filas
        if (query.trim() === "") {
            rows.forEach(row => row.style.display = '');
        }
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
