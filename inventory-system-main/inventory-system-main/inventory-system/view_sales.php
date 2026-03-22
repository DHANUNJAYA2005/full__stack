<?php
include 'db.php';

$sql = "SELECT sales.sale_id, products.product_name, sales.quantity, sales.sale_date
        FROM sales
        JOIN products ON sales.product_id = products.product_id
        ORDER BY sales.sale_id DESC";

$result = $conn->query($sql);
?>

<!DOCTYPE html>
<html>
<head>
    <title>Sales History</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
            background-color: #f4f6f9;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
        }

        .page-title {
            text-align: center;
            color: #000;   /* BLACK TITLE */
            margin-top: 40px;
        }

        .table-container {
            width: 90%;
            margin: 30px auto;
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background-color: #2c3e50;
            color: white;
            padding: 12px;
            text-align: center;
        }

        td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #eee;
        }

        tr:hover {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

<div class="nav">
    <a href="dashboard.php">Dashboard</a>
    <a href="index.php">Add Product</a>
    <a href="view_products.php">View Products</a>
    <a href="sales.php">Sales</a>
    <a href="view_sales.php">Sales History</a>
</div>

<h2 class="page-title">Sales History</h2>

<div class="table-container">
    <table>
        <tr>
            <th>Sale ID</th>
            <th>Product Name</th>
            <th>Quantity Sold</th>
            <th>Sale Date</th>
        </tr>

        <?php
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                echo "<tr>";
                echo "<td>".$row['sale_id']."</td>";
                echo "<td>".$row['product_name']."</td>";
                echo "<td>".$row['quantity']."</td>";
                echo "<td>".$row['sale_date']."</td>";
                echo "</tr>";
            }
        } else {
            echo "<tr><td colspan='4'>No sales records found</td></tr>";
        }
        ?>
    </table>
</div>

</body>
</html>