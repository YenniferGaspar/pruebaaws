<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Proveedor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <h2>Editar Proveedor</h2>
    <form action="editarSupplier" method="post">
        <input type="hidden" name="id" value="${supplier.id}"/>
        <div class="mb-3">
            <label for="name" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="name" name="name" value="${supplier.name}" required>
        </div>
        <div class="mb-3">
            <label for="direction" class="form-label">Dirección</label>
            <input type="text" class="form-control" id="direction" name="direction" value="${supplier.direction}" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Correo Electrónico</label>
            <input type="email" class="form-control" id="email" name="email" value="${supplier.email}" required>
        </div>
        <button type="submit" class="btn btn-danger">Actualizar Proveedor</button>
        <a href="listarSupplier" class="btn btn-secondary">Cancelar</a>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
