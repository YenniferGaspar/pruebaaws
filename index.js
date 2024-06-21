const express = require("express");
const path = require("path");
const mysql = require("mysql");
const bodyParser = require("body-parser");

const app = express();
const port = 3000;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const conexion = mysql.createConnection({
    host: "localhost",
    port: 33060,
    database: "pruebaaws",
    user: "root",
    password: "yennifer1"
});

conexion.connect((err) => {
    if (err) {
        console.error('Error de conexión a la base de datos: ' + err.stack);
        return;
    }
    console.log('Conectado a la base de datos.');
});

app.post('/submit-form', (req, res) => {
    const {
        nombre,
        apellido,
        telefono,
    } = req.body;

    const query = `INSERT INTO persona (nombre, apellido, telefono)
    VALUES (?, ?, ?)`;

    conexion.query(query, [
        nombre,
        apellido,
        telefono,
    ], (err, result) => {
        if (err) {
            console.error('Error al insertar datos: ' + err.stack);
            res.status(500).send('Ocurrió un error al procesar tu consulta.');
            return;
        }

        res.status(200).send('Tu consulta ha sido procesada exitosamente.');
    });
});

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

app.listen(port, () => {
    console.log(`Servidor escuchando en http://127.0.0.1:${port}`);
});
