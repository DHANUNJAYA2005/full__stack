<?php
session_start();
include 'db.php';

if(isset($_POST['login'])){
    $username = $_POST['username'];
    $password = $_POST['password'];

    $result = mysqli_query($conn,"SELECT * FROM users WHERE username='$username'");
    $row = mysqli_fetch_assoc($result);

    if($row && password_verify($password,$row['password'])){
        $_SESSION['user']=$row['username'];
        header("Location: dashboard.php");
    }else{
        echo "<h3 style='color:red;text-align:center;'>Invalid Username or Password</h3>";
    }
}
?>

<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<style>
body{
    font-family:Arial;
    background:linear-gradient(to right,#43e97b,#38f9d7);
    text-align:center;
    padding-top:120px;
}
input{
    padding:12px;
    margin:10px;
    width:250px;
}
button{
    padding:10px 20px;
    background:#000;
    color:white;
    border:none;
}
</style>
</head>
<body>

<h2>Login</h2>

<form method="POST">
<input type="text" name="username" placeholder="Enter Username" required><br>
<input type="password" name="password" placeholder="Enter Password" required><br>
<button name="login">Login</button>
</form>

</body>
</html>