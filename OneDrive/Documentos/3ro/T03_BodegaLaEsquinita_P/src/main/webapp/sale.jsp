<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrar Venta</title>
</head>
<body>
<h2>Formulario de Venta</h2>
<form action="processSale.jsp" method="post">
    <label for="customer_id">ID del Cliente:</label>
    <input type="number" name="customer_id" required><br><br>

    <label for="seller_id">ID del Vendedor:</label>
    <input type="number" name="seller_id" required><br><br>

    <label for="date_sale">Fecha de Venta:</label>
    <input type="date" name="date_sale" required><br><br>

    <label for="payment_method">Método de Pago:</label>
    <input type="text" name="payment_method" required><br><br>

    <label for="product_id">ID del Producto:</label>
    <input type="number" name="product_id" required><br><br>

    <label for="total_sale">Total de la Venta:</label>
    <input type="number" step="0.01" name="total_sale" required><br><br>

    <input type="submit" value="Registrar Venta">
</form>
</body>
</html>
