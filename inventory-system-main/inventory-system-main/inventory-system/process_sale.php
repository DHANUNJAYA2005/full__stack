<?php
include 'db.php';

$product_id = $_POST['product_id'];
$quantity = $_POST['quantity'];
$date = date("Y-m-d");

// Get current stock
$result = $conn->query("SELECT stock FROM products WHERE product_id=$product_id");
$row = $result->fetch_assoc();
$current_stock = $row['stock'];

if ($quantity > $current_stock) {
    echo "Not enough stock!";
    exit();
}

// Insert sale record
$conn->query("INSERT INTO sales (product_id, quantity, sale_date)
              VALUES ($product_id, $quantity, '$date')");

// Update product stock
$new_stock = $current_stock - $quantity;
$conn->query("UPDATE products SET stock=$new_stock WHERE product_id=$product_id");

// Redirect to sales history
header("Location: view_sales.php");
exit();
?>