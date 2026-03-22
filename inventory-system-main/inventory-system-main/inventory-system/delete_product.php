<?php
include 'db.php';

$id = $_GET['id'];

$sql = "DELETE FROM products WHERE product_id=$id";

if ($conn->query($sql) === TRUE) {
    header("Location: view_products.php");
} else {
    echo "Error deleting record: " . $conn->error;
}
?>