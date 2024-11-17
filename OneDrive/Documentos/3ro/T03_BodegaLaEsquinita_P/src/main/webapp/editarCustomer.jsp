<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Cliente</title>
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
    <h2 class="mb-4">Editar Cliente</h2>
    <form id="customerForm" action="${pageContext.request.contextPath}/editarCustomer" method="post">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="id" value="${customer.id}">
        <div id="errorAlert" class="alert alert-danger d-none">
            Error al enviar el formulario. Por favor, revise los campos.
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="first_name" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="first_name" name="name" value="${customer.name}" required>
                <div class="valid-feedback">¡Se ve bien!</div>
                <div class="invalid-feedback">Los nombres no deben contener números.</div>
            </div>
            <div class="col-md-6 mb-3">
                <label for="last_name" class="form-label">Apellido</label>
                <input type="text" class="form-control" id="last_name" name="last_name" value="${customer.lastName}" required>
                <div class="valid-feedback">¡Se ve bien!</div>
                <div class="invalid-feedback">Los apellidos no deben contener números.</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="document_type" class="form-label">Tipo de Documento</label>
                <select class="form-control" id="document_type" name="document_type" required>
                    <option value="DNI" ${customer.documentType != null && customer.documentType.equals("DNI") ? 'selected' : ''}>DNI</option>
                    <option value="CNE" ${customer.documentType != null && customer.documentType.equals("CNE") ? 'selected' : ''}>CNE</option>
                </select>
                <div class="valid-feedback">¡Se ve bien!</div>
                <div class="invalid-feedback">Seleccione un tipo de documento.</div>
            </div>
            <div class="col-md-6 mb-3">
                <label for="number_document" class="form-label">Número de Documento</label>
                <input type="text" class="form-control" id="number_document" name="number_document" value="${customer.numberDocument}" required>
                <div class="valid-feedback">¡Se ve bien!</div>
                <div class="invalid-feedback">Ingrese un número de documento válido.</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3 mb-3">
                <label for="birthdate" class="form-label">Fecha de Nacimiento</label>
                <input type="date" class="form-control" id="birthdate" name="birthdate" value="${formattedBirthdate}" required>
                <div class="valid-feedback">¡Se ve bien!</div>
                <div class="invalid-feedback">Debe ser mayor de 18 años.</div>
            </div>
            <div class="col-md-3 mb-3">
                <label for="mobile" class="form-label">Celular</label>
                <input type="text" class="form-control" id="mobile" name="phone" value="${customer.phone}" required>
                <div class="valid-feedback">¡Se ve bien!</div>
                <div class="invalid-feedback">Solo se permiten números.</div>
            </div>
            <div class="col-md-3 mb-3">
                <label for="email" class="form-label">Correo Electrónico</label>
                <input type="email" class="form-control" id="email" name="email" value="${customer.email}" required>
                <div class="valid-feedback">¡Se ve bien!</div>
                <div class="invalid-feedback">Ingrese un correo electrónico válido.</div>
            </div>
        </div>
        <div class="col-md-12 mb-3">
            <label for="address" class="form-label">Dirección</label>
            <input type="text" class="form-control" id="address" name="address" value="${customer.address}" required>
            <div class="valid-feedback">¡Se ve bien!</div>
            <div class="invalid-feedback">La dirección es obligatoria.</div>
        </div>

        <button type="submit" class="btn btn-success">Guardar Cambios</button>
        <a href="listarCustomers" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const form = document.getElementById("customerForm");
        const inputs = form.querySelectorAll("input, select");
        const errorAlert = document.createElement("div");
        errorAlert.id = "errorAlert";
        errorAlert.classList.add("alert", "alert-danger", "d-none");
        errorAlert.textContent = "Por favor, corrige los errores antes de enviar.";
        form.prepend(errorAlert);

        function aplicarValidacionesIniciales() {
            inputs.forEach(input => {
                if (input.value.trim() && validarCampo(input)) {
                    input.classList.remove("is-invalid");
                    input.classList.add("is-valid");
                } else {
                    input.classList.remove("is-valid");
                    input.classList.add("is-invalid");
                }
            });
        }

        inputs.forEach(input => {
            input.addEventListener("input", () => validarCampo(input));
            input.addEventListener("change", () => validarCampo(input));
        });

        aplicarValidacionesIniciales();

        form.addEventListener("submit", (event) => {
            event.preventDefault();
            let esValido = true;
            errorAlert.classList.add("d-none");

            inputs.forEach(input => {
                if (!validarCampo(input)) {
                    esValido = false;
                }
            });

            if (esValido) {
                form.submit();
            } else {
                errorAlert.classList.remove("d-none");
            }
        });

        function validarCampo(input) {
            let esValido = true;

            if (input.hasAttribute("required") && !input.value.trim()) {
                esValido = false;
            }

            switch (input.id) {
                case "first_name":
                case "last_name":
                    if (/\d/.test(input.value)) {
                        esValido = false;
                    }
                    break;
                case "number_document":
                    if (!/^\d+$/.test(input.value)) {
                        esValido = false;
                    }
                    break;
                case "mobile":
                    if (!/^\d{9}$/.test(input.value)) {
                        esValido = false;
                    }
                    break;
                case "email":
                    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(input.value)) {
                        esValido = false;
                    }
                    break;
                case "birthdate":
                    const fechaNacimiento = new Date(input.value);
                    const fechaActual = new Date();
                    if (fechaNacimiento >= fechaActual) {
                        esValido = false;
                    }
                    break;
            }

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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>