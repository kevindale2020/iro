<?php 
include '../config.php'; //this code will allow you to connect to your database

if (isset($_GET['seen'])) 
{
	$clear = mysqli_query($database, "UPDATE notifications SET is_seen_admin = 2 WHERE is_seen_admin = 1");

	echo "<script>window.history.back();</script>";
}

 ?>