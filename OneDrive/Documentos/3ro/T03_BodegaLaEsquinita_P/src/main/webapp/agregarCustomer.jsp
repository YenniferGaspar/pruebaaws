<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Agregar Cliente</title>
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
    <h2 class="mb-3">Agregar Cliente</h2>
    <form class="row g-3 needs-validation" action="${pageContext.request.contextPath}/agregarCustomer" method="post" id="customerForm" onsubmit="return validateForm()">
        <div id="errorAlert" class="alert alert-danger d-none">
            Error al enviar el formulario. Por favor, revise los campos.
        </div>

        <div class="col-md-4">
            <label for="name" class="form-label">Nombres</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Ingrese los nombres del cliente..." required>
            <div class="valid-feedback">¡Se ve bien!</div>
            <div class="invalid-feedback">Los nombres no deben contener números.</div>
        </div>

        <div class="col-md-4">
            <label for="last_name" class="form-label">Apellidos</label>
            <input type="text" class="form-control" id="last_name" name="last_name" placeholder="Ingrese los apellidos del cliente..." required>
            <div class="valid-feedback">¡Se ve bien!</div>
            <div class="invalid-feedback">Los apellidos no deben contener números.</div>
        </div>

        <div class="col-md-3">
            <label for="document_type" class="form-label">Tipo de Documento</label>
            <select class="form-select" id="document_type" name="document_type" required>
                <option selected disabled value="">Seleccione su tipo de documento</option>
                <option value="DNI">DNI</option>
                <option value="CNE">CNE</option>
            </select>
            <div class="valid-feedback">¡Se ve bien!</div>
            <div class="invalid-feedback">Seleccione un tipo de documento.</div>
        </div>

        <div class="col-md-3">
            <label for="number_document" class="form-label">Número de Documento</label>
            <input type="text" class="form-control" id="number_document" name="number_document" placeholder="Ingrese el número de documento..." required>
            <div class="valid-feedback">¡Se ve bien!</div>
            <div class="invalid-feedback">Ingrese un número de documento válido.</div>
        </div>

        <div class="col-md-3">
            <label for="birthdate" class="form-label">Fecha de Nacimiento</label>
            <input type="date" class="form-control" id="birthdate" name="birthdate" required>
            <div class="valid-feedback">¡Se ve bien!</div>
            <div class="invalid-feedback">Debe ser mayor de 18 años.</div>
        </div>

        <div class="col-md-3">
            <label for="phone" class="form-label">Teléfono</label>
            <input type="text" class="form-control" id="phone" name="phone" placeholder="Ingrese el número de teléfono..." required>
            <div class="valid-feedback">¡Se ve bien!</div>
            <div class="invalid-feedback">Solo se permiten números.</div>
        </div>

        <div class="col-md-4">
            <label for="email" class="form-label">Correo Electrónico</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="Ingrese el correo electrónico..." required>
            <div class="valid-feedback">¡Se ve bien!</div>
            <div class="invalid-feedback">Ingrese un correo electrónico válido.</div>
        </div>
        <div class="col-md-6">
            <label for="address" class="form-label">Dirección</label>
            <input type="text" class="form-control" id="address" name="address" placeholder="Ingrese la dirección del cliente..." required>
            <div class="valid-feedback">¡Se ve bien!</div>
            <div class="invalid-feedback">La dirección es obligatoria.</div>
        </div>
        <div class="col-12">
            <button class="btn btn-primary" type="submit">Añadir Cliente</button>
            <a href="listarCustomers" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const form = document.getElementById("customerForm");
        const inputs = form.querySelectorAll("input, select");
        const errorAlert = document.getElementById("errorAlert");

        // Añadir eventos de validación en tiempo real
        inputs.forEach(input => {
            input.addEventListener("input", () => validarCampo(input));
            input.addEventListener("change", () => validarCampo(input));
        });

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

            // Si todos los campos son válidos, enviar el formulario (puedes usar AJAX aquí si lo necesitas)
            if (esValido) {
                form.submit(); // Elimina esta línea si solo quieres manejar el envío con AJAX
                // enviarFormularioAJAX(); // Si prefieres enviar el formulario con AJAX, descomenta esto
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
                case "last_name":
                    const nameRegex = /^[a-zA-Z\s]+$/;
                    if (!nameRegex.test(input.value)) {
                        esValido = false; // Validar solo letras y espacios
                    }
                    break;
                case "document_type":
                    if (!input.value) {
                        esValido = false; // El tipo de documento es obligatorio
                    }
                    break;
                case "number_document":
                    const documentType = document.getElementById("document_type").value;
                    const numberDocument = input.value;
                    let numberDocumentPattern;
                    if (documentType === "DNI") {
                        numberDocumentPattern = /^\d{8}$/;
                    } else if (documentType === "CNE") {
                        numberDocumentPattern = /^\d{9,20}$/;
                    }
                    if (!numberDocumentPattern.test(numberDocument)) {
                        esValido = false; // Validación del número de documento
                    }
                    break;
                case "birthdate":
                    const birthDateValue = new Date(input.value);
                    const today = new Date();
                    const ageLimit = new Date();
                    ageLimit.setFullYear(today.getFullYear() - 18);
                    if (birthDateValue > ageLimit) {
                        esValido = false; // Verificar si es mayor de 18 años
                    }
                    break;
                case "phone":
                    const phone = input.value;
                    const phoneRegex = /^\d{9}$/; // Solo 9 dígitos
                    if (!phoneRegex.test(phone)) {
                        esValido = false; // Si no son 9 dígitos, es inválido
                    }
                    break;
                case "email":
                    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
                    if (!emailRegex.test(input.value)) {
                        esValido = false; // Validar formato de correo electrónico
                    }
                    break;
                case "address":
                    if (input.value.trim() === '') {
                        esValido = false; // Validar que no esté vacío
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

    // Función para manejar el cambio del tipo de documento y actualizar la validación
    function validateDocumentNumber() {
        const documentType = document.getElementById("document_type").value;
        const numberDocument = document.getElementById("number_document");
        if (documentType === "DNI") {
            numberDocument.setAttribute("pattern", "\\d{8}");
            numberDocument.setAttribute("title", "El DNI debe tener exactamente 8 dígitos.");
        } else if (documentType === "CNE") {
            numberDocument.setAttribute("pattern", "\\d{9,20}");
            numberDocument.setAttribute("title", "El CNE debe tener entre 9 y 20 dígitos.");
        }
        numberDocument.value = ""; // Limpiar el campo si cambia el tipo de documento
    }
</script>
</body>
</html>