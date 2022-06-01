<?php

//to hide all errors :)
error_reporting(E_ERROR | E_PARSE);

//set timezone to 08:00+
date_default_timezone_set('Asia/Manila');

$dbHost = 'localhost';
$dbName = 'iro_db';
$dbUsername = 'root';
$dbPassword = '';

$connection = mysqli_connect($dbHost, $dbUsername, $dbPassword, $dbName);

if(!$connection) {
	die('Connect Error: ' . mysqli_connect_errno());
}
?>