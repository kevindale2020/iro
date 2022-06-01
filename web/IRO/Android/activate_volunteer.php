<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$userid = $_POST['id'];
	$date = date("F j, Y, g:i a");
	
	$sql = mysqli_query($connection, "UPDATE `volunteer` SET `is_active` = '1' WHERE `volunteer`.`user_id` = $userid");

	$sql2 = mysqli_query($connection, "UPDATE `user_roles` SET `role_id` = '3' WHERE `user_roles`.`user_id` = $userid");

	$sql3 = mysqli_query($connection, "INSERT INTO `notifications_admin` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`) VALUES (NULL, '$userid', '3', 'Volunteer Account Activation', 'Account has been set to active', '$date', '0')");

	if(!$sql || !$sql2 || !$sql3) {
		$result["success"] = "0";
		$result["message"] = "error";
	} else {
		$result["success"] = "1";
		$result["message"] = "success";
	}
	echo json_encode($result);
	mysqli_close($connection);

}
?>