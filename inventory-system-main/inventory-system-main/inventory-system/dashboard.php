<?php
include 'db.php';

// Total Products
$total_products = $conn->query("SELECT COUNT(*) AS count FROM products")
                        ->fetch_assoc()['count'];

// Total Sales Transactions
$total_sales = $conn->query("SELECT COUNT(*) AS count FROM sales")
                     ->fetch_assoc()['count'];

// Low Stock Products (<5)
$low_stock = $conn->query("SELECT COUNT(*) AS count FROM products WHERE stock < 5")
                   ->fetch_assoc()['count'];

// Total Revenue
$total_revenue = $conn->query("SELECT SUM(products.price * sales.quantity) AS revenue
                               FROM sales
                               JOIN products ON sales.product_id = products.product_id")
                      ->fetch_assoc()['revenue'];

if($total_revenue == null){
    $total_revenue = 0;
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
            background-color: #f4f6f9;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
        }

        .dashboard {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 60px;
            flex-wrap: wrap;
        }

        .card {
            background: white;
            padding: 30px;
            width: 220px;
            text-align: center;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.15);
            transition: 0.3s ease;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .card h3 {
            margin-bottom: 10px;
            color: #2c3e50;
        }

        .card p {
            font-size: 22px;
            font-weight: bold;
            color: #000;
        }

        .page-title {
            text-align: center;
            color: #000;   /* BLACK COLOR */
            margin-top: 40px;
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

<h2 class="page-title">Inventory Dashboard</h2>

<div class="dashboard">
    <div class="card">
        <h3>Total Products</h3>
        <p><?php echo $total_products; ?></p>
    </div>

    <div class="card">
        <h3>Total Sales</h3>
        <p><?php echo $total_sales; ?></p>
    </div>

    <div class="card">
        <h3>Low Stock</h3>
        <p><?php echo $low_stock; ?></p>
    </div>

    <div class="card">
        <h3>Total Revenue</h3>
        <p>₹<?php echo $total_revenue; ?></p>
    </div>
</div>

</body>
</html>