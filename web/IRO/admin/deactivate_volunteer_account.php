<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (isset($_GET['deactivate'])) 
{
	$id = $_GET['deactivate'];
	$success = "Volunteer account has been deactivated successfully";
	$date_today = date("F j, Y, g:i a");
	
	$details = mysqli_query($database, "SELECT * FROM volunteer WHERE user_id = $id");

	$res = mysqli_fetch_array($details);

	$volunteer_id = $res['volunteer_id'];

	$deactivate_account = mysqli_query($database, "UPDATE user_roles SET role_id = 1 WHERE user_id = $id");

	$deactivate_account = mysqli_query($database, "UPDATE volunteer SET is_active = 0 WHERE volunteer_id = $volunteer_id");

	echo "<script>window.location.href='admin_viewuser.php?success=$success'</script>"; 

	$send_notification = mysqli_query($database, "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$id', '3', 'Volunteer', 'Your volunteer account has been deactivated by admin.', '$date_today', '0', '0')");
}
 ?>