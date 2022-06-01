<?php
include '../config.php'; //this code will allow you to connect to your database


if ($database->connect_error) {
    die("Connection failed: " . $database->connect_error);
} 

$sql = "SELECT * FROM notifications_admin WHERE is_seen = 0";
$result = $database->query($sql);

echo $result->num_rows;



mysqli_close($database);
?>