<?php 
include '../config.php'; //this code will allow you to connect to your database

if (isset($_GET['notification_id'])) 
{
	$notification_id = $_GET['notification_id'];

	$remove_seen = "UPDATE notifications_admin SET is_seen = 2 WHERE notification_id = $notification_id";

	if (mysqli_query($database, $remove_seen)) 
	{
		echo "<script>window.history.back();</script>";
	}
}

if (isset($_GET['remove_all'])) 
{
	$remove_all = $_GET['remove_all'];

	$remove_all = "UPDATE notifications_admin SET is_seen = 2 WHERE is_seen = 1";

	if (mysqli_query($database, $remove_all)) 
	{
		echo "<script>window.history.back();</script>";
	}
}
 ?>