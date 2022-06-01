<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$id = $_POST['id'];
	$current_password = mysqli_real_escape_string($connection, $_POST['current_password']);
	$new_password = mysqli_real_escape_string($connection, $_POST['new_password']);
	$confirm_password = mysqli_real_escape_string($connection, $_POST['confirm_password']);

	$check = mysqli_query($connection, "Select * from users where users.user_id = $id and users.user_password = '$current_password'");
	if(mysqli_num_rows($check)==1) {
		$sql = mysqli_query($connection, "Update users set users.user_password = '$new_password' where users.user_id = $id");
		$result["success"] = "1";
		$result["message"] = "success";
		echo json_encode($result);
	} else {
		$result["success"] = "0";
		$result["message"] = "This is not your current password.";
		echo json_encode($result);
	}

	mysqli_close($connection);

}
?>