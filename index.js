const path = require("path");
const express = require("express");
const bodyParser = require("body-parser");
const mysql = require("mysql");

const app = express();
const port = 3000;

// Middleware para parsear body de las peticiones
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// Configuración de la conexión a la base de datos MySQL
let conexion = mysql.createConnection({
    host: "127.0.0.1",
    port: 33060,
    database: "sitioweb",
    user: "root",
    password: "yennifer1",
});

// Conectar a la base de datos
conexion.connect((err) => {
    if (err) {
        console.error('Error de conexión a la base de datos: ' + err.stack);
        return;
    }
    console.log('Conectado a la base de datos.');
});

// Servir archivos estáticos desde la carpeta 'public'
app.use(express.static(path.join(__dirname, 'public')));

// Servir el archivo HTML del formulario
app.get('/', (req, res) => {
    console.log('Solicitud recibida en la ruta /');
    res.sendFile(path.join(__dirname, 'index.html'));
});

// Manejar el envío del formulario para insertar en la base de datos
app.post('/submit-form', (req, res) => {
    const { nombre, telefono, mensaje } = req.body;
    console.log('Datos recibidos:');
    console.log('Nombre:', nombre);
    console.log('Teléfono:', telefono);
    console.log('Mensaje:', mensaje);

    // Consulta SQL para insertar datos en la tabla específica (persona)
    const query = 'INSERT INTO persona (nombre, telefono, mensaje) VALUES (?, ?, ?)';

    // Ejecutar la consulta SQL con los valores del formulario
    conexion.query(query, [nombre, telefono, mensaje], (err, result) => {
        if (err) {
            console.error('Error al insertar datos: ' + err.stack);
            res.status(500).json({ message: 'Ocurrió un error al procesar tu consulta.' });
            return;
        }
        res.status(200).json({ message: 'Tu consulta ha sido procesada exitosamente.' });
    });
});

// Manejar la actualización de datos en la base de datos
app.put('/actualizar-form/:index', (req, res) => {
    const index = req.params.index;
    const { nombre, telefono, mensaje } = req.body;
    console.log('Datos recibidos para actualizar:');
    console.log('Nombre:', nombre);
    console.log('Teléfono:', telefono);
    console.log('Mensaje:', mensaje);

    // Consulta SQL para actualizar datos en la tabla específica (persona)
    const query = 'UPDATE persona SET nombre = ?, telefono = ?, mensaje = ? WHERE id = ?';

    // Ejecutar la consulta SQL con los valores del formulario
    conexion.query(query, [nombre, telefono, mensaje, index], (err, result) => {
        if (err) {
            console.error('Error al actualizar datos: ' + err.stack);
            res.status(500).json({ message: 'Ocurrió un error al procesar tu consulta.' });
            return;
        }
        res.status(200).json({ message: 'Los datos han sido actualizados exitosamente.' });
    });
});

// Manejar la eliminación de datos en la base de datos
app.delete('/eliminar-form/:index', (req, res) => {
    const index = req.params.index;
    console.log('Índice recibido para eliminar:', index);

    // Consulta SQL para eliminar datos en la tabla específica (persona)
    const query = 'DELETE FROM persona WHERE id = ?';

    // Ejecutar la consulta SQL con los valores del formulario
    conexion.query(query, [index], (err, result) => {
        if (err) {
            console.error('Error al eliminar datos: ' + err.stack);
            res.status(500).json({ message: 'Ocurrió un error al procesar tu consulta.' });
            return;
        }
        res.status(200).json({ message: 'Los datos han sido eliminados exitosamente.' });
    });
});

// Iniciar el servidor
app.listen(port, () => {
    console.log(`Servidor iniciado en http://localhost:${port}`);
});
