<?php
include 'db.php';

$name = $_POST['product_name'];
$price = $_POST['price'];
$stock = $_POST['stock'];

$sql = "INSERT INTO products (product_name, price, stock)
        VALUES ('$name', '$price', '$stock')";

if ($conn->query($sql) === TRUE) {
    echo "Product Added Successfully!";
} else {
    echo "Error: " . $conn->error;
}
?>