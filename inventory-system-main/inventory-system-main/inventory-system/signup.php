<?php
include 'db.php';

if(isset($_POST['signup'])){
    $username = $_POST['username'];
    $password = password_hash($_POST['password'], PASSWORD_DEFAULT);

    $sql = "INSERT INTO users(username,password)
            VALUES('$username','$password')";
    
    if(mysqli_query($conn,$sql)){
        header("Location: login.php");
    }else{
        echo "Error!";
    }
}
?>

<!DOCTYPE html>
<html>
<head>
<title>Signup</title>
<style>
body{
    font-family:Arial;
    background:linear-gradient(to right,#4facfe,#00f2fe);
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
    background:#333;
    color:white;
    border:none;
}
</style>
</head>
<body>

<h2>Create Account</h2>

<form method="POST">
<input type="text" name="username" placeholder="Enter Username" required><br>
<input type="password" name="password" placeholder="Enter Password" required><br>
<button name="signup">Sign Up</button>
</form>

</body>
</html>