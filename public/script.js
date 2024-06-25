// Este código debería estar en validar.js

let listaEmpleados = [];

document.addEventListener('DOMContentLoaded', () => {
    const formulario = document.getElementById('formulario');
    const btnAgregar = document.getElementById('btnAgregar');
    
    formulario.addEventListener('submit', validarFormulario);
    btnAgregar.addEventListener('click', agregarEmpleado);

    // Agregar eventos input para validación en tiempo real
    const nombreInput = document.getElementById('nombre');
    const telefonoInput = document.getElementById('telefono');
    const mensajeInput = document.getElementById('mensaje');

    nombreInput.addEventListener('input', () => bloquearCaracteresNoPermitidos(nombreInput, /^[^a-zA-Z]+$/));
    telefonoInput.addEventListener('input', () => validarTelefono(telefonoInput));
    mensajeInput.addEventListener('input', () => bloquearCaracteresNoPermitidos(mensajeInput, /^[^a-zA-Z]+$/));
});

function bloquearCaracteresNoPermitidos(inputElement, regex) {
    const valor = inputElement.value;
    const contieneNumeros = /\d/.test(valor); // Verificar si el valor contiene números
    if (contieneNumeros) {
        alert(`Lo siento no puede contener ningun tipo de números`);
        inputElement.value = valor.replace(/\d/g, ''); // Eliminar todos los números del valor
    }
}

function validarTelefono(inputElement) {
    const valor = inputElement.value;
    const contieneLetras = /[a-zA-Z]/.test(valor); // Verificar si el valor contiene letras
    if (contieneLetras) {
        alert(`Lo siento no puede contener ningun tipo de letra`);
        
        inputElement.value = valor.replace(/[a-zA-Z]/g, ''); // Eliminar todas las letras del valor
    }
}

function validarFormulario(e) {
    e.preventDefault();

    const nombre = document.getElementById('nombre').value.trim();
    const telefono = document.getElementById('telefono').value.trim();
    const mensaje = document.getElementById('mensaje').value.trim();

    if (nombre === '' || telefono === '' || mensaje === '') {
        return;
    }

    agregarEmpleado();
}

// Resto del código




function agregarEmpleado() {
    const nombre = document.getElementById('nombre').value.trim();
    const telefono = document.getElementById('telefono').value.trim();
    const mensaje = document.getElementById('mensaje').value.trim();

    listaEmpleados.push({ nombre, telefono, mensaje });

    mostrarEmpleados();

    limpiarFormulario();
    alert(`${nombre} Se agregó correctamente`);
}

function mostrarEmpleados() {
    const divEmpleados = document.querySelector('.div-empleados');
    divEmpleados.innerHTML = '';

    listaEmpleados.forEach((empleado, index) => {
        const { nombre, telefono, mensaje } = empleado;
        const numeroEmpleado = index + 1 < 10 ? `0${index + 1}` : index + 1; // Agrega un cero delante si es menor que 10
        const empleadoHTML = `
            <p>${numeroEmpleado}: ${nombre} - ${telefono} - ${mensaje} <button class="btn-editar" onclick="editarEmpleado(${index})">Editar</button> <button class="btn-eliminar" onclick="eliminarEmpleado(${index})">Eliminar</button></p>
        `;
        divEmpleados.innerHTML += empleadoHTML;
    });
}


function editarEmpleado(index) {
    const empleado = listaEmpleados[index];

    // Llenar el formulario con los valores del empleado seleccionado
    document.getElementById('nombre').value = empleado.nombre;
    document.getElementById('telefono').value = empleado.telefono;
    document.getElementById('mensaje').value = empleado.mensaje;

    // Ocultar el botón de agregar y mostrar el botón de actualizar
    const btnAgregar = document.getElementById('btnAgregar');
    btnAgregar.style.display = 'none';

    const formulario = document.getElementById('formulario');
    const btnActualizar = document.createElement('button');
    btnActualizar.textContent = 'Actualizar';
    btnActualizar.setAttribute('type', 'button');
    btnActualizar.setAttribute('id', 'btnActualizar');
    btnActualizar.setAttribute('data-index', index);
    btnActualizar.addEventListener('click', () => {
        actualizarEmpleado(index);
        btnActualizar.remove();
        btnAgregar.style.display = 'block';
    });

    // Agregar la clase btn-actualizar al botón de actualizar
    btnActualizar.classList.add('btn-actualizar');

    // Reemplazar el botón de agregar con el botón de actualizar en el formulario
    formulario.appendChild(btnActualizar);
}




function actualizarEmpleado(index) {
    const nombreInput = document.getElementById('nombre').value.trim();
    const telefonoInput = document.getElementById('telefono').value.trim();
    const mensajeInput = document.getElementById('mensaje').value.trim();

    if (nombreInput === '' || telefonoInput === '' || mensajeInput === '') {
        return;
    }

    listaEmpleados[index] = { nombre: nombreInput, telefono: telefonoInput, mensaje: mensajeInput };

    mostrarEmpleados();

    limpiarFormulario();
    alert('Empleado actualizado correctamente');
}

document.getElementById('btnActualizar').addEventListener('click', () => {
    const btnActualizar = document.querySelector('#formulario button[type="button"]');
    if (btnActualizar) {
        const index = parseInt(btnActualizar.getAttribute('data-index'));
        actualizarEmpleado(index);
        btnActualizar.remove();
        const btnAgregar = document.getElementById('btnAgregar');
        btnAgregar.style.display = 'block';
    }
});



function eliminarEmpleado(index) {
    if (confirm("¿Está seguro de eliminar este empleado?")) {
        listaEmpleados.splice(index, 1);
        mostrarEmpleados();
        alert('Empleado eliminado correctamente');
    }
}

function limpiarFormulario() {
    document.getElementById('nombre').value = '';
    document.getElementById('telefono').value = '';
    document.getElementById('mensaje').value = '';
}
btnActualizar.classList.add('btn-actualizar');