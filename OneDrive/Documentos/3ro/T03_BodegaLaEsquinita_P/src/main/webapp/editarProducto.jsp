<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Editar Producto</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f5f5f5;
    }
    .container {
      margin-top: 50px;
    }
    .is-valid {
      border-color: green;
    }
    .is-invalid {
      border-color: red;
    }
    .error-message {
      color: red;
      font-size: 0.9em;
      margin-top: 5px;
    }
  </style>
</head>
<body>
<div class="container">
  <h2 class="mb-4">Editar Producto</h2>
  <form class="row g-3 needs-validation" action="${pageContext.request.contextPath}/editarProduct" method="post" id="productForm" novalidate>
    <input type="hidden" name="id" value="${producto.id}"/> <!-- Asegúrate de que estás pasando el ID -->

    <div id="errorAlert" class="alert alert-danger d-none">
      Error al enviar el formulario. Por favor, revise los campos.
    </div>

    <div class="col-md-4">
      <label for="name" class="form-label">Nombre</label>
      <input type="text" class="form-control" id="name" name="name" value="${producto.name}" required>
      <div class="valid-feedback">
        ¡Se ve bien!
      </div>
      <div class="invalid-feedback">
        El nombre no debe contener números.
      </div>
    </div>

    <div class="col-md-4">
      <label for="description" class="form-label">Descripción</label>
      <input type="text" class="form-control" id="description" name="description" value="${producto.description}" required>
      <div class="valid-feedback">
        ¡Se ve bien!
      </div>
      <div class="invalid-feedback">
        Proporciona una descripción para el producto.
      </div>
    </div>

    <div class="col-md-3">
      <label for="category" class="form-label">Categoría</label>
      <select class="form-select" id="category" name="category" required>
        <option value="">Seleccione una categoría...</option>
        <option value="Embutidos" ${'Embutidos'.equals(producto.category) ? 'selected' : ''}>Embutidos</option>
        <option value="Bebidas" ${'Bebidas'.equals(producto.category) ? 'selected' : ''}>Bebidas</option>
        <option value="Lácteos" ${'Lácteos'.equals(producto.category) ? 'selected' : ''}>Lácteos</option>
        <option value="Snacks" ${'Snacks'.equals(producto.category) ? 'selected' : ''}>Snacks</option>
        <option value="Conservas" ${'Conservas'.equals(producto.category) ? 'selected' : ''}>Conservas</option>
        <option value="Condimentos" ${'Condimentos'.equals(producto.category) ? 'selected' : ''}>Condimentos</option>
        <option value="Higiene" ${'Higiene'.equals(producto.category) ? 'selected' : ''}>Higiene</option>
        <option value="Abarrotes" ${'Abarrotes'.equals(producto.category) ? 'selected' : ''}>Abarrotes</option>
        <option value="Frutas y Verduras" ${'Frutas y Verduras'.equals(producto.category) ? 'selected' : ''}>Frutas y Verduras</option>
        <option value="Carnes" ${'Carnes'.equals(producto.category) ? 'selected' : ''}>Carnes</option>
        <option value="Granos" ${'Granos'.equals(producto.category) ? 'selected' : ''}>Granos</option>
        <option value="Panadería" ${'Panadería'.equals(producto.category) ? 'selected' : ''}>Panadería</option>
        <option value="Confitería" ${'Confitería'.equals(producto.category) ? 'selected' : ''}>Confitería</option>
        <option value="Cuidado Personal" ${'Cuidado Personal'.equals(producto.category) ? 'selected' : ''}>Cuidado Personal</option>
        <option value="Limpieza" ${'Limpieza'.equals(producto.category) ? 'selected' : ''}>Limpieza</option>
        <option value="Mascotas" ${'Mascotas'.equals(producto.category) ? 'selected' : ''}>Mascotas</option>
        <option value="Congelados" ${'Congelados'.equals(producto.category) ? 'selected' : ''}>Congelados</option>
      </select>
      <div class="valid-feedback">
        ¡Se ve bien!
      </div>
      <div class="invalid-feedback" id="categoryError">${requestScope.categoryError != null ? requestScope.categoryError : ''}>
        Seleccione una categoría.
      </div>
    </div>

    <div class="col-md-3">
      <label for="trade_mark" class="form-label">Marca</label>
      <select class="form-select" id="trade_mark" name="trade_mark" required>
        <option value="">Seleccione una marca...</option>
        <option value="Gloria" ${'Gloria'.equals(producto.tradeMark) ? 'selected' : ''}>Gloria</option>
        <option value="Alicorp" ${'Alicorp'.equals(producto.tradeMark) ? 'selected' : ''}>Alicorp</option>
        <option value="San Fernando" ${'San Fernando'.equals(producto.tradeMark) ? 'selected' : ''}>San Fernando</option>
        <option value="Inka Kola" ${'Inka Kola'.equals(producto.tradeMark) ? 'selected' : ''}>Inka Kola</option>
        <option value="Backus" ${'Backus'.equals(producto.tradeMark) ? 'selected' : ''}>Backus</option>
        <option value="D'Onofrio" ${'D\'Onofrio'.equals(producto.tradeMark) ? 'selected' : ''}>D'Onofrio</option>
        <option value="Ajinomoto" ${'Ajinomoto'.equals(producto.tradeMark) ? 'selected' : ''}>Ajinomoto</option>
        <option value="Laive" ${'Laive'.equals(producto.tradeMark) ? 'selected' : ''}>Laive</option>
        <option value="Perú Cola" ${'Perú Cola'.equals(producto.tradeMark) ? 'selected' : ''}>Perú Cola</option>
        <option value="Cusqueña" ${'Cusqueña'.equals(producto.tradeMark) ? 'selected' : ''}>Cusqueña</option>
        <option value="Milkito" ${'Milkito'.equals(producto.tradeMark) ? 'selected' : ''}>Milkito</option>
        <option value="Danlac" ${'Danlac'.equals(producto.tradeMark) ? 'selected' : ''}>Danlac</option>
        <option value="Bonlé" ${'Bonlé'.equals(producto.tradeMark) ? 'selected' : ''}>Bonlé</option>
        <option value="Winter's" ${'Winter\'s'.equals(producto.tradeMark) ? 'selected' : ''}>Winter's</option>
        <option value="Field" ${'Field'.equals(producto.tradeMark) ? 'selected' : ''}>Field</option>
        <option value="Nicolini" ${'Nicolini'.equals(producto.tradeMark) ? 'selected' : ''}>Nicolini</option>
        <option value="Florida" ${'Florida'.equals(producto.tradeMark) ? 'selected' : ''}>Florida</option>
        <option value="Compass" ${'Compass'.equals(producto.tradeMark) ? 'selected' : ''}>Compass</option>
        <option value="Fanny" ${'Fanny'.equals(producto.tradeMark) ? 'selected' : ''}>Fanny</option>
        <option value="Sapolio" ${'Sapolio'.equals(producto.tradeMark) ? 'selected' : ''}>Sapolio</option>
        <option value="Intradevco" ${'Intradevco'.equals(producto.tradeMark) ? 'selected' : ''}>Intradevco</option>
        <option value="San Mateo" ${'San Mateo'.equals(producto.tradeMark) ? 'selected' : ''}>San Mateo</option>
        <option value="Ajíno-men" ${'Ajíno-men'.equals(producto.tradeMark) ? 'selected' : ''}>Ajíno-men</option>
        <option value="Kola Real" ${'Kola Real'.equals(producto.tradeMark) ? 'selected' : ''}>Kola Real</option>
        <option value="Negrita" ${'Negrita'.equals(producto.tradeMark) ? 'selected' : ''}>Negrita</option>
        <option value="Molitalia" ${'Molitalia'.equals(producto.tradeMark) ? 'selected' : ''}>Molitalia</option>
        <option value="Royal" ${'Royal'.equals(producto.tradeMark) ? 'selected' : ''}>Royal</option>
      </select>
      <div class="valid-feedback">
        ¡Se ve bien!
      </div>
      <div class="invalid-feedback" id="tradeMarkError">${requestScope.tradeMarkError != null ? requestScope.tradeMarkError : ''}>
        Seleccione una Marca.
      </div>
    </div>

    <div class="col-md-4">
      <label for="stock" class="form-label">Cantidad</label>
      <input type="number" class="form-control" id="stock" name="stock" value="${producto.stock}" min="0" required>
      <div class="valid-feedback">
        ¡Se ve bien!
      </div>
      <div class="invalid-feedback">
        Ingrese una cantidad válida.
      </div>
    </div>

    <div class="col-md-4">
      <label for="price" class="form-label">Precio</label>
      <input type="number" class="form-control" id="price" name="price" value="${producto.price}" min="0" step="0.01" required>
      <div class="valid-feedback">
        ¡Se ve bien!
      </div>
      <div class="invalid-feedback">
        Ingrese un precio válido.
      </div>
    </div>

    <div class="col-md-6">
      <label for="expiry_date" class="form-label">Fecha de Vencimiento</label>
      <input type="date" class="form-control" id="expiry_date" name="expiry_date" value="${formattedDate}" required>
      <div class="invalid-feedback">La fecha debe ser después de 10 meses.</div>
      <div class="valid-feedback">
        ¡Se ve bien!
      </div>
    </div>

    <div class="col-md-5">
      <label for="code_product" class="form-label">Código del Producto</label>
      <input type="text" class="form-control" id="code_product" name="code_product" value="${producto.codeProduct}" placeholder="Ingrese el codigo del producto..." required pattern="[A-Za-z0-9]{4}">
      <div class="invalid-feedback">Proporciona un código válido.</div>
      <div class="valid-feedback">
        ¡Se ve bien!
      </div>
      <div class="invalid-feedback">
        Ingrese un codigo válido.
      </div>
    </div>

    <div class="col-12">
      <button class="btn btn-primary" type="submit">Actualizar Producto</button>
      <a href="listarProductos" class="btn btn-secondary">Cancelar</a>
    </div>
  </form>
</div>
<script>
  document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("productForm");
    const inputs = form.querySelectorAll("input, select");
    const errorAlert = document.getElementById("errorAlert");

    // Función para aplicar la clase is-valid a los campos con valores válidos
    function aplicarValidacionesIniciales() {
      inputs.forEach(input => {
        if (input.value.trim() && validarCampo(input)) {
          // Si el valor está presente y el campo es válido, asigna la clase is-valid
          input.classList.remove("is-invalid");
          input.classList.add("is-valid");
        } else {
          // Si no es válido, asigna la clase is-invalid (por si los valores previos no eran válidos)
          input.classList.remove("is-valid");
          input.classList.add("is-invalid");
        }
      });
    }

    // Añadir eventos de validación en tiempo real
    inputs.forEach(input => {
      input.addEventListener("input", () => validarCampo(input));
      input.addEventListener("change", () => validarCampo(input));
    });

    // Aplicar las validaciones iniciales cuando la página se carga
    aplicarValidacionesIniciales();

    // Manejar el evento submit
    form.addEventListener("submit", (event) => {
      event.preventDefault(); // Prevenir el envío normal del formulario
      let esValido = true;

      // Limpiar el mensaje de error
      errorAlert.classList.add("d-none");

      // Validar cada campo
      inputs.forEach(input => {
        if (!validarCampo(input)) {
          esValido = false; // Si algún campo no es válido
        }
      });

      // Si todos los campos son válidos, enviar el formulario
      if (esValido) {
        form.submit(); // Elimina esta línea si solo quieres manejar el envío con AJAX
      } else {
        errorAlert.classList.remove("d-none");
        errorAlert.textContent = "Por favor, corrige los errores antes de enviar.";
      }
    });

    // Función para validar cada campo
    function validarCampo(input) {
      let esValido = true;

      // Validaciones básicas
      if (input.hasAttribute("required") && !input.value.trim()) {
        esValido = false;
      }

      // Validaciones específicas
      switch (input.id) {
        case "name":
          if (/\d/.test(input.value)) {
            esValido = false; // No permitir números en el nombre
          }
          break;
        case "description":
          if (input.value.trim() === '') {
            esValido = false; // La descripción no debe estar vacía
          }
          break;
        case "category":
          if (input.value === '' || input.value === null) {
            esValido = false; // La categoría debe estar seleccionada
          }
          break;
        case "expiry_date":
          const fechaVencimiento = new Date(input.value);
          const fechaLimite = new Date();
          fechaLimite.setMonth(fechaLimite.getMonth() + 10);
          if (fechaVencimiento < fechaLimite) {
            esValido = false; // La fecha debe ser al menos 10 meses en el futuro
          }
          break;
        case "stock":
          if (input.value <= 0) {
            esValido = false; // La cantidad debe ser mayor que 0
          }
          break;
        case "price":
          if (parseFloat(input.value) <= 0) {
            esValido = false; // El precio debe ser mayor que cero
          }
          break;
        case "code_product":
          if (!/^[A-Za-z0-9]{4}$/.test(input.value)) {
            esValido = false; // Código debe tener exactamente 4 caracteres alfanuméricos
          }
          break;
      }

      // Aplicar clases de Bootstrap
      if (esValido) {
        input.classList.remove("is-invalid");
        input.classList.add("is-valid");
      } else {
        input.classList.remove("is-valid");
        input.classList.add("is-invalid");
      }

      return esValido;
    }
  });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>