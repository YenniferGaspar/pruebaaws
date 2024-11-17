<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Agregar Producto</title>
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
    <h2 class="mb-3">Agregar Producto</h2>
    <form class="row g-3 needs-validation" action="${pageContext.request.contextPath}/agregarProduct" method="post" id="productForm">
    <div id="errorAlert" class="alert alert-danger d-none">
            Error al enviar el formulario. Por favor, revise los campos.
        </div>

        <div class="col-md-4">
            <label for="name" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Ingrese el nombre del producto..." required>
            <div class="valid-feedback">
                ¡Se ve bien!
            </div>
            <div class="invalid-feedback">
                El nombre no debe contener números.
            </div>
        </div>

        <div class="col-md-4">
            <label for="description" class="form-label">Descripción</label>
            <input type="text" class="form-control" id="description" name="description" placeholder="Ingrese la descripción del producto..." required>
            <div class="valid-feedback">
                ¡Se ve bien!
            </div>
            <div class="invalid-feedback">Proporciona una descripción oara el producto.</div>
        </div>

        <div class="col-md-3">
            <label for="category" class="form-label">Categoría</label>
            <select class="form-select" id="category" name="category" required>
                <option selected disabled value="">Seleccine una categoria...</option>
                <option value="Embutidos" ${'Embutidos'.equals(requestScope.category) ? 'selected' : ''}>Embutidos</option>
                <option value="Bebidas" ${'Bebidas'.equals(requestScope.category) ? 'selected' : ''}>Bebidas</option>
                <option value="Lácteos" ${'Lácteos'.equals(requestScope.category) ? 'selected' : ''}>Lácteos</option>
                <option value="Snacks" ${'Snacks'.equals(requestScope.category) ? 'selected' : ''}>Snacks</option>
                <option value="Conservas" ${'Conservas'.equals(requestScope.category) ? 'selected' : ''}>Conservas</option>
                <option value="Condimentos" ${'Condimentos'.equals(requestScope.category) ? 'selected' : ''}>Condimentos</option>
                <option value="Higiene" ${'Higiene'.equals(requestScope.category) ? 'selected' : ''}>Higiene</option>
                <option value="Abarrotes" ${'Abarrotes'.equals(requestScope.category) ? 'selected' : ''}>Abarrotes</option>
                <option value="Frutas y Verduras" ${'Frutas y Verduras'.equals(requestScope.category) ? 'selected' : ''}>Frutas y Verduras</option>
                <option value="Carnes" ${'Carnes'.equals(requestScope.category) ? 'selected' : ''}>Carnes</option>
                <option value="Granos" ${'Granos'.equals(requestScope.category) ? 'selected' : ''}>Granos</option>
                <option value="Panadería" ${'Panadería'.equals(requestScope.category) ? 'selected' : ''}>Panadería</option>
                <option value="Confitería" ${'Confitería'.equals(requestScope.category) ? 'selected' : ''}>Confitería</option>
                <option value="Cuidado Personal" ${'Cuidado Personal'.equals(requestScope.category) ? 'selected' : ''}>Cuidado Personal</option>
                <option value="Limpieza" ${'Limpieza'.equals(requestScope.category) ? 'selected' : ''}>Limpieza</option>
                <option value="Mascotas" ${'Mascotas'.equals(requestScope.category) ? 'selected' : ''}>Mascotas</option>
                <option value="Congelados" ${'Congelados'.equals(requestScope.category) ? 'selected' : ''}>Congelados</option>
            </select>
            <div class="invalid-feedback">
                Selecciona una categoría válida.
            </div>
        </div>

        <div class="col-md-3">
            <label for="trade_mark" class="form-label">Marca</label>
            <select class="form-select" id="trade_mark" name="trade_mark"  required>
                <option selected disabled value="">Seleccione una marca...</option>
                <option value="Gloria" ${'Gloria'.equals(requestScope.trade_mark) ? 'selected' : ''}>Gloria</option>
                <option value="Alicorp" ${'Alicorp'.equals(requestScope.trade_mark) ? 'selected' : ''}>Alicorp</option>
                <option value="San Fernando" ${'San Fernando'.equals(requestScope.trade_mark) ? 'selected' : ''}>San Fernando</option>
                <option value="Inka Kola" ${'Inka Kola'.equals(requestScope.trade_mark) ? 'selected' : ''}>Inka Kola</option>
                <option value="Backus" ${'Backus'.equals(requestScope.trade_mark) ? 'selected' : ''}>Backus</option>
                <option value="D'Onofrio" ${'D\'Onofrio'.equals(requestScope.trade_mark) ? 'selected' : ''}>D'Onofrio</option>
                <option value="Ajinomoto" ${'Ajinomoto'.equals(requestScope.trade_mark) ? 'selected' : ''}>Ajinomoto</option>
                <option value="Laive" ${'Laive'.equals(requestScope.trade_mark) ? 'selected' : ''}>Laive</option>
                <option value="Perú Cola" ${'Perú Cola'.equals(requestScope.trade_mark) ? 'selected' : ''}>Perú Cola</option>
                <option value="Cusqueña" ${'Cusqueña'.equals(requestScope.trade_mark) ? 'selected' : ''}>Cusqueña</option>
                <option value="Milkito" ${'Milkito'.equals(requestScope.trade_mark) ? 'selected' : ''}>Milkito</option>
                <option value="Danlac" ${'Danlac'.equals(requestScope.trade_mark) ? 'selected' : ''}>Danlac</option>
                <option value="Bonlé" ${'Bonlé'.equals(requestScope.trade_mark) ? 'selected' : ''}>Bonlé</option>
                <option value="Winter's" ${'Winter\'s'.equals(requestScope.trade_mark) ? 'selected' : ''}>Winter's</option>
                <option value="Field" ${'Field'.equals(requestScope.trade_mark) ? 'selected' : ''}>Field</option>
                <option value="Nicolini" ${'Nicolini'.equals(requestScope.trade_mark) ? 'selected' : ''}>Nicolini</option>
                <option value="Florida" ${'Florida'.equals(requestScope.trade_mark) ? 'selected' : ''}>Florida</option>
                <option value="Compass" ${'Compass'.equals(requestScope.trade_mark) ? 'selected' : ''}>Compass</option>
                <option value="Fanny" ${'Fanny'.equals(requestScope.trade_mark) ? 'selected' : ''}>Fanny</option>
                <option value="Sapolio" ${'Sapolio'.equals(requestScope.trade_mark) ? 'selected' : ''}>Sapolio</option>
                <option value="Intradevco" ${'Intradevco'.equals(requestScope.trade_mark) ? 'selected' : ''}>Intradevco</option>
                <option value="San Mateo" ${'San Mateo'.equals(requestScope.trade_mark) ? 'selected' : ''}>San Mateo</option>
                <option value="Ajíno-men" ${'Ajíno-men'.equals(requestScope.trade_mark) ? 'selected' : ''}>Ajíno-men</option>
                <option value="Kola Real" ${'Kola Real'.equals(requestScope.trade_mark) ? 'selected' : ''}>Kola Real</option>
                <option value="Negrita" ${'Negrita'.equals(requestScope.trade_mark) ? 'selected' : ''}>Negrita</option>
                <option value="Molitalia" ${'Molitalia'.equals(requestScope.trade_mark) ? 'selected' : ''}>Molitalia</option>
                <option value="Royal" ${'Royal'.equals(requestScope.trade_mark) ? 'selected' : ''}>Royal</option>
            </select>
            <div class="invalid-feedback">
                Selecciona una marca válida.
            </div>
        </div>

        <div class="col-md-4">
            <label for="stock" class="form-label">Cantidad</label>
            <input type="number" class="form-control" id="stock" name="stock" placeholder="Ingrese la cantidad del producto..." required>
            <div class="invalid-feedback">Proporciona una cantidad válida.</div>
        </div>

        <div class="col-md-4">
            <label for="price" class="form-label">Precio</label>
            <input type="number" class="form-control" id="price" name="price" placeholder="Ingrese el precio del producto..." required step="0.01">
            <div class="invalid-feedback">Proporciona un precio válido.</div>
        </div>

        <div class="col-md-6">
            <label for="expiry_date" class="form-label">Fecha de Vencimiento</label>
            <input type="date" class="form-control" id="expiry_date" name="expiry_date" required>
            <div class="invalid-feedback">La fecha debe ser después de 10 meses.</div>
            <div class="valid-feedback">
                ¡Se ve bien!
            </div>
        </div>

        <div class="col-md-5">
            <label for="code_product" class="form-label">Código del Producto</label>
            <input type="text" class="form-control" id="code_product" name="code_product" placeholder="Ingrese el codigo del producto..." required pattern="[A-Za-z0-9]{4}">
            <div class="invalid-feedback">Proporciona un código válido.</div>
            <div class="valid-feedback">
                ¡Se ve bien!
            </div>
        </div>

        <div class="col-12">
            <button class="btn btn-primary" type="submit">Añadir Producto</button>
            <a href="listarProductos" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const form = document.getElementById("productForm");
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
</body>
</html>