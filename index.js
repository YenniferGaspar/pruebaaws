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
const conexion = mysql.createConnection({
     host: "127.0.0.1",
     port: 33060,
     database: "pruebaaws",
     user: "root",
     password: "yennifer1"
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


// Manejar el envío del formulario en la misma página
app.post('/submit-form', (req, res) => {
     const { nombre, apellido, telefono } = req.body;
     console.log('Datos recibidos:');
     console.log('Nombre:', nombre);
     console.log('Apellido:', apellido);
     console.log('Teléfono:', telefono);
     // Consulta SQL para insertar datos en la tabla específica (formulario)
     const query = 'INSERT INTO persona (nombre, apellido, telefono) VALUES (?, ?, ?)';


     // Ejecutar la consulta SQL con los valores del formulario
     conexion.query(query, [nombre, apellido, telefono], (err, result) => {
          if (err) {
               console.error('Error al insertar datos: ' + err.stack);
               res.status(500).json({ message: 'Ocurrió un error al procesar tu consulta.' });
               return;
          }
          res.status(200).json({ message: 'Tu consulta ha sido procesada exitosamente.' });
     });
});


// Iniciar el servidor
app.listen(port, () => {
     console.log(`Servidor iniciado en http://localhost:${port}`);
});
