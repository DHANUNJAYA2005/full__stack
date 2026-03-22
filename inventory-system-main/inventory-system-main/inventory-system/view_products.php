<?php
include 'db.php';

$sql = "SELECT * FROM products";
$result = $conn->query($sql);
?>

<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="nav">
    <a href="dashboard.php">Dashboard</a>
    <a href="index.php">Add Product</a>
    <a href="view_products.php">View Products</a>
    <a href="sales.php">Sales</a>
    <a href="view_sales.php">Sales History</a>
</div>

<h2 class="page-title">Product Inventory</h2>

<div class="table-container">
    <table>
        <tr>
            <th>ID</th>
            <th>Product Name</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Status</th>
            <th>Action</th>
        </tr>

        <?php
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {

                echo "<tr>";
                echo "<td>".$row['product_id']."</td>";
                echo "<td>".$row['product_name']."</td>";
                echo "<td>₹".$row['price']."</td>";
                echo "<td>".$row['stock']."</td>";

                if($row['stock'] < 5){
                    echo "<td><span class='badge-low'>Low Stock</span></td>";
                } else {
                    echo "<td><span class='badge-ok'>In Stock</span></td>";
                }

                echo "<td>
                        <a class='delete-btn' href='delete_product.php?id=".$row['product_id']."' 
                        onclick=\"return confirm('Are you sure you want to delete this product?')\">
                        Delete
                        </a>
                      </td>";

                echo "</tr>";
            }
        } else {
            echo "<tr><td colspan='6'>No products available</td></tr>";
        }
        ?>
    </table>
</div>

</body>
</html>