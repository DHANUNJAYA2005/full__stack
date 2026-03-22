<!DOCTYPE html>
<html>
<head>
    <title>Add Product</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .form-container {
            width: 400px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }

        .form-container h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #2c3e50;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 6px;
            font-weight: 500;
            color: #34495e;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        .form-group input:focus {
            border-color: #34495e;
            outline: none;
        }

        .submit-btn {
            width: 100%;
            padding: 10px;
            background-color: #34495e;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 15px;
            cursor: pointer;
        }

        .submit-btn:hover {
            background-color: #2c3e50;
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

<div class="form-container">
    <h2>Add New Product</h2>

    <form action="add_product.php" method="POST">
        
        <div class="form-group">
            <label>Product Name</label>
            <input type="text" name="product_name" required>
        </div>

        <div class="form-group">
            <label>Price (₹)</label>
            <input type="number" name="price" required>
        </div>

        <div class="form-group">
            <label>Stock Quantity</label>
            <input type="number" name="stock" required>
        </div>

        <button type="submit" class="submit-btn">Add Product</button>
    </form>
</div>

</body>
</html>