<?php session_start(); ?>
<!DOCTYPE html>
<html>
<head>
    <title>Inventory System</title>
    <style>
        body{
            font-family: Arial;
            text-align:center;
            background: linear-gradient(to right,#667eea,#764ba2);
            color:white;
            padding-top:150px;
        }
        .btn{
            padding:10px 20px;
            background:white;
            color:#764ba2;
            text-decoration:none;
            margin:10px;
            border-radius:5px;
            font-weight:bold;
        }
        .btn:hover{
            background:#ddd;
        }
    </style>
</head>
<body>

<h1>Welcome to Inventory System</h1>

<a href="login.php" class="btn">Login</a>
<a href="signup.php" class="btn">Sign Up</a>

</body>
</html>