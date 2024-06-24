let listaEmpleados = [];
const objEmpleado = {
    id: '',
    nombre: '',
    apellido: '',
    telefono: ''
};
let editando = false;

const formulario = document.querySelector('#formulario');
const nombreInput = document.querySelector('#nombre');
const apellidoInput = document.querySelector('#apellido');
const telefonoInput = document.querySelector('#telefono');
const btnAgregarInput = document.querySelector('#btnAgregar');

formulario.addEventListener('submit', validarFormulario);

function validarFormulario(e) {
    e.preventDefault();

    if (nombreInput.value === '' || apellidoInput.value === '' || telefonoInput.value === '') {
        alert('Todos los campos se deben llenar');
        return;
    }

    if (editando) {
        editarEmpleado();
        editando = false;
    } else {
        objEmpleado.id = Date.now(); // Asigna un ID temporal
        objEmpleado.nombre = nombreInput.value;
        objEmpleado.apellido = apellidoInput.value;
        objEmpleado.telefono = telefonoInput.value;

        enviarDatosAJAX(objEmpleado); // Envía los datos del empleado al servidor
    }
}

function enviarDatosAJAX(empleado) {
    fetch('/submit-form', {
        method: 'POST',  // Método de la solicitud HTTP
        headers: {
            'Content-Type': 'application/json'  // Tipo de contenido que se está enviando (JSON en este caso)
        },
        body: JSON.stringify(empleado)  // Convierte el objeto 'empleado' a formato JSON y lo envía como cuerpo de la solicitud
    })
    .then(response => {
        if (!response.ok) {  // Verifica si la respuesta no es exitosa (código de estado 200-299)
            throw new Error('Error al enviar los datos.');
        }
        return response.json();  // Parsea la respuesta como JSON y la devuelve
    })
    .then(data => {
        listaEmpleados.push({ ...empleado });  // Agrega el objeto 'empleado' al array 'listaEmpleados'
        mostrarEmpleados();  // Actualiza la interfaz mostrando los empleados

        formulario.reset();  // Limpia los campos del formulario después del envío
        limpiarObjeto();  // Limpia el objeto 'empleado'

        btnAgregarInput.textContent = 'Agregar';  // Restablece el texto del botón a 'Agregar'
        editando = false;  // Restablece la variable de estado 'editando' a false
    })
    .catch(error => {  // Captura errores de la solicitud fetch o del procesamiento posterior
        console.error('Error:', error);  // Registra el error en la consola
        alert('Hubo un problema al procesar tu solicitud. Por favor, intenta nuevamente.');  // Muestra una alerta al usuario
    });
}

function limpiarObjeto() {
    objEmpleado.id = '';
    objEmpleado.nombre = '';
    objEmpleado.apellido = '';
    objEmpleado.telefono = '';
}

function mostrarEmpleados() {
    limpiarHTML();

    const divEmpleados = document.querySelector('.div-empleados');

    listaEmpleados.forEach(empleado => {
        const { id, nombre, apellido, telefono } = empleado;

        const parrafo = document.createElement('p');
        parrafo.textContent = `${id} - ${nombre} - ${apellido} - ${telefono}`;

        const editarBoton = document.createElement('button');
        editarBoton.onclick = () => cargarEmpleado(empleado);
        editarBoton.textContent = 'Editar';
        editarBoton.classList.add('btn', 'btn-editar');
        parrafo.appendChild(editarBoton);

        const eliminarBoton = document.createElement('button');
        eliminarBoton.onclick = () => eliminarEmpleado(id);
        eliminarBoton.textContent = 'Eliminar';
        eliminarBoton.classList.add('btn', 'btn-eliminar');
        parrafo.appendChild(eliminarBoton);

        const hr = document.createElement('hr');

        divEmpleados.appendChild(parrafo);
        divEmpleados.appendChild(hr);
    });
}

function cargarEmpleado(empleado) {
    const { id, nombre, apellido, telefono } = empleado;

    nombreInput.value = nombre;
    apellidoInput.value = apellido;
    telefonoInput.value = telefono;

    objEmpleado.id = id;

    formulario.querySelector('button[type="submit"]').textContent = 'Actualizar';

    editando = true;
}

function editarEmpleado() {
    objEmpleado.nombre = nombreInput.value;
    objEmpleado.apellido = apellidoInput.value;
    objEmpleado.telefono = telefonoInput.value;

    listaEmpleados = listaEmpleados.map(empleado => {
        if (empleado.id === objEmpleado.id) {
            return { ...objEmpleado };
        }
        return empleado;
    });

    limpiarHTML();
    mostrarEmpleados();
    formulario.reset();

    btnAgregarInput.textContent = 'Agregar';
    editando = false;
}

function eliminarEmpleado(id) {
    listaEmpleados = listaEmpleados.filter(empleado => empleado.id !== id);

    limpiarHTML();
    mostrarEmpleados();
}

function limpiarHTML() {
    const divEmpleados = document.querySelector('.div-empleados');
    while (divEmpleados.firstChild) {
        divEmpleados.removeChild(divEmpleados.firstChild);
    }
}
