<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (isset($_GET['item_id'])) 
{
	$item_id = $_GET['item_id'];
	$success = "Merchandise has been remove successfully!";
	$delete_item = "DELETE FROM items WHERE item_id = $item_id";

	if (mysqli_query($database, $delete_item)) 
	{
		echo "<script>window.location.href='admin_viewmerchandise.php?success=$success';</script>";
	}
}

 ?>