<?php

$dbHost = 'localhost';
$dbName = 'iro_db';
$dbUsername = 'root';
$dbPassword = '';

$connection = mysqli_connect($dbHost, $dbUsername, $dbPassword, $dbName) or die(mysqli_error($connection));
?>