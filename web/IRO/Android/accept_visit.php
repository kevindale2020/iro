<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$id = $_POST['id'];
	$id2 = $_POST['id2'];
	$date_notified = date("F j, Y, g:i a");

	$sql = mysqli_query($connection, "Delete from notifications where notification_id = $id2");

	$sql2 = mysqli_query($connection, "INSERT INTO `notifications_admin` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`) VALUES (NULL, '$id', '8', 'Home Visit', 'Agree to home visit', '$date_notified', '0')");

	if(!$sql || !$sql2) {
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