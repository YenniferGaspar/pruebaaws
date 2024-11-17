<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>La Esquinita - Iniciar Sesión</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f5f5f5;
        }
        .login-container {
            display: flex;
            border: 1px solid #ddd;
            background-color: white;
            width: 800px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .login-container .left-section {
            background-color: #d32f2f;
            color: white;
            width: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 30px;
            font-weight: bold;
        }
        .login-container .right-section {
            padding: 40px;
            width: 50%;
        }
        .form-control, .btn {
            margin-top: 10px;
        }
        .user-icon {
            text-align: center;
            margin-bottom: 20px;
        }
        .user-icon img {
            width: 80px;
            border-radius: 50%;
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="left-section">
        LA ESQUINITA BODEGA
    </div>
    <div class="right-section">
        <div class="user-icon">
            <img src="https://via.placeholder.com/80" alt="User Icon">
        </div>
        <form action="login" method="post">
            <div class="mb-3">
                <label for="username" class="form-label">Usuario</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="Ingrese su usuario" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Contraseña</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Ingrese su contraseña" required>
            </div>
            <button type="submit" class="btn btn-danger w-100">Iniciar Sesión</button>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
