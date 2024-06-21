let listaEmpleados = [];

const objEmpleado = {
    id: '',
    nombre: '',
    apellido: '',
    telefono: ''  // Nuevo campo
}

let editando = false;

const formulario = document.querySelector('#formulario');
const nombreInput = document.querySelector('#nombre');
const apellidoInput = document.querySelector('#apellido');
const telefonoInput = document.querySelector('#telefono');  // Nuevo campo
const btnAgregarInput = document.querySelector('#btnAgregar');

formulario.addEventListener('submit', validarFormulario);

function validarFormulario(e) {
    e.preventDefault();

    if(nombreInput.value === '' || apellidoInput.value === '' || telefonoInput.value === '') {  // Cambio realizado
        alert('Todos los campos se deben llenar');
        return;
    }

    if(editando) {
        editarEmpleado();
        editando = false;
    } else {
        objEmpleado.id = Date.now();
        objEmpleado.nombre = nombreInput.value;
        objEmpleado.apellido = apellidoInput.value;
        objEmpleado.telefono = telefonoInput.value;  // Nuevo campo

        agregarEmpleado();
    }
}

function agregarEmpleado() {
    listaEmpleados.push({...objEmpleado});

    mostrarEmpleados();

    formulario.reset();
    limpiarObjeto();
}

function limpiarObjeto() {
    objEmpleado.id = '';
    objEmpleado.nombre = '';
    objEmpleado.apellido = '';
    objEmpleado.telefono = '';  // Nuevo campo
}

function mostrarEmpleados() {
    limpiarHTML();

    const divEmpleados = document.querySelector('.div-empleados');
    
    listaEmpleados.forEach(empleado => {
        const {id, nombre, apellido, telefono} = empleado;  // Nuevo campo

        const parrafo = document.createElement('p');
        parrafo.textContent = `${id} - ${nombre} - ${apellido} - ${telefono}`;  // Nuevo campo
        parrafo.dataset.id = id;

        const editarBoton = document.createElement('button');
        editarBoton.onclick = () => cargarEmpleado(empleado);
        editarBoton.textContent = 'Editar';
        editarBoton.classList.add('btn', 'btn-editar');
        parrafo.append(editarBoton);

        const eliminarBoton = document.createElement('button');
        eliminarBoton.onclick = () => eliminarEmpleado(id);
        eliminarBoton.textContent = 'Eliminar';
        eliminarBoton.classList.add('btn', 'btn-eliminar');
        parrafo.append(eliminarBoton);

        const hr = document.createElement('hr');

        divEmpleados.appendChild(parrafo);
        divEmpleados.appendChild(hr);
    });
}

function cargarEmpleado(empleado) {
    const {id, nombre, apellido, telefono} = empleado;  // Nuevo campo

    nombreInput.value = nombre;
    apellidoInput.value = apellido;
    telefonoInput.value = telefono;  // Nuevo campo

    objEmpleado.id = id;

    formulario.querySelector('button[type="submit"]').textContent = 'Actualizar';
    
    editando = true;
}

function editarEmpleado() {
    objEmpleado.nombre = nombreInput.value;
    objEmpleado.apellido = apellidoInput.value;
    objEmpleado.telefono = telefonoInput.value;  // Nuevo campo

    listaEmpleados.map(empleado => {
        if(empleado.id === objEmpleado.id) {
            empleado.id = objEmpleado.id;
            empleado.nombre = objEmpleado.nombre;
            empleado.apellido = objEmpleado.apellido;
            empleado.telefono = objEmpleado.telefono;  // Nuevo campo
        }
    });

    limpiarHTML();
    mostrarEmpleados();
    formulario.reset();

    formulario.querySelector('button[type="submit"]').textContent = 'Agregar';
    
    editando = false;
}

function eliminarEmpleado(id) {
    listaEmpleados = listaEmpleados.filter(empleado => empleado.id !== id);

    limpiarHTML();
    mostrarEmpleados();
}

function limpiarHTML() {
    const divEmpleados = document.querySelector('.div-empleados');
    while(divEmpleados.firstChild) {
        divEmpleados.removeChild(divEmpleados.firstChild);
    }
}
