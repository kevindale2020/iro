<?php
include '../config.php'; //this code will allow you to connect to your database

$result = mysqli_query($database, "SELECT SUM(amount) AS value_sum FROM donations WHERE is_verified = 1");
$data = mysqli_fetch_assoc($result);
$sum = $data['value_sum'];

$english_format_number = number_format($sum, 2);

echo $english_format_number;

mysqli_close($database);
?>