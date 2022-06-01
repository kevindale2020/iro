<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (isset($_GET['delete_pet'])) 
{
  $pet_id = $_GET['delete_pet'];

  $deletepet = "DELETE FROM pets WHERE pet_id = $pet_id";

  if (mysqli_query($database, $deletepet)) 
  {
  	$showAll = 'Pet has been removed successfully';

  	echo "<script>window.location.href='admin_viewpet.php?showAll=$showAll';</script>";
  }

}

 ?>