<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Agregar Proveedor</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f5f5f5;
    }
    .container {
      margin-top: 50px;
    }
  </style>
</head>
<body>
<div class="container">
  <h2 class="mb-4">Agregar Proveedor</h2>
  <form action="${pageContext.request.contextPath}/agregarSupplier" method="post">
    <div class="mb-3">
      <label for="name" class="form-label">Nombre</label>
      <input type="text" class="form-control" id="name" name="name" required>
    </div>
    <div class="mb-3">
      <label for="direction" class="form-label">Dirección</label>
      <input type="text" class="form-control" id="direction" name="direction" required>
    </div>
    <div class="mb-3">
      <label for="email" class="form-label">Correo Electrónico</label>
      <input type="email" class="form-control" id="email" name="email" required>
    </div>
    <button type="submit" class="btn btn-danger">Añadir Proveedor</button>
    <a href="listarSupplier" class="btn btn-secondary">Cancelar</a>
  </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
