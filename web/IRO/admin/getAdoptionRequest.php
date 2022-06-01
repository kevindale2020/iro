<?php
include '../config.php'; //this code will allow you to connect to your database


if ($database->connect_error) {
    die("Connection failed: " . $database->connect_error);
} 

$sql = "SELECT * FROM documents WHERE doc_status_id = 1 and doc_type_id = 1";
$result = $database->query($sql);

echo $result->num_rows;



mysqli_close($database);
?>