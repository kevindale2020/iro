<?php 
include '../config.php'; //this code will allow you to connect to your database

if (isset($_GET['id'])) 
{
	$id = $_GET['id'];
	$success = 'Event has been remove successfully';

	$delete_event = mysqli_query($database, "UPDATE events SET is_active = 0 WHERE id = $id");

	echo "<script>window.location.href='admin_viewevent.php?success=$success';</script>";
}
 ?>